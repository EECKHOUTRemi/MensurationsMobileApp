package com.example.mensurationsimc

import androidx.annotation.RequiresApi

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.mensurationsimc.database.AppDatabase
import com.example.mensurationsimc.database.Measurement
import com.example.mensurationsimc.database.WeightBmi
import com.example.mensurationsimc.ui.theme.Inter
import com.example.mensurationsimc.ui.theme.MensurationsIMCTheme
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.Line


class MainActivity : ComponentActivity() {
    val tag = "MENSUIVI"
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(tag, "Main activity created")
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.Transparent.toArgb(), White.toArgb()),
        )
        setContent {
            val navController = rememberNavController()
            Scaffold(
                topBar = {
                    Header(
                        navController = navController
                    )
                },
                modifier = Modifier
                    .fillMaxSize(),
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                ) {
                    RootNavHost(
                        navController = navController,
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(tag, "Main activity started")
    }

    override fun onResume() {
        super.onResume()
        Log.i(tag, "Main activity resumed")
    }

    override fun onPause() {
        super.onPause()
        Log.i(tag, "Main activity paused")
    }

    override fun onStop() {
        super.onStop()
        Log.i(tag, "Main activity stopped")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(tag, "Main activity destroyed")
    }
}

@Composable
fun Accueil(modifier: Modifier, context: Context) {
    val db = remember {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "mensuivi_db"
        ).build()
    }

    var weightBmiList by remember { mutableStateOf<List<WeightBmi>>(emptyList()) }
    var measurementList by remember { mutableStateOf<List<Measurement>>(emptyList()) }
    var height by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        weightBmiList = db.weightBmiDao().getAll()
        measurementList = db.measurementDao().getAll()
        height = db.profileDao().getHeight()
    }

    // Transformation pour les charts
    val poidsMap = weightBmiList.associate { it.date to it.weight }
    val imcMap = weightBmiList.associate { it.date to it.bmi }
    val poitrineMap = measurementList.associate { it.date to it.chest }
    val tailleMap = measurementList.associate { it.date to it.waist }
    val hanchesMap = measurementList.associate { it.date to it.hips }
    val cuissesMap = measurementList.associate { it.date to it.thighs }

    MensurationsIMCTheme(darkTheme = false) {
        Column(modifier = modifier) {
            if (weightBmiList.isEmpty() && measurementList.isEmpty()) {
                Text(
                    text = "Aucune donnée disponible. Veuillez ajouter des mesures et des poids via les pages Vos poids et Vos mensurations.",
                    modifier = Modifier.padding(16.dp),
                    fontFamily = Inter,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            } else {
                Row {
                    StatCarousel(
                        data = mapOf(
                            "Dernier poids enregistré" to (poidsMap.values.lastOrNull()?.toString() ?: "-"),
                            "Taille actuelle" to (height?.toString() ?: "-"),
                            "Dernier IMC calculé" to (imcMap.values.lastOrNull()?.toString() ?: "-"),
                        ),
                    )
                }
                Row (modifier = Modifier.height(300.dp).padding(16.dp)) {
                    Chart(
                        data = mapOf(
                            "IMC" to imcMap.values.toList(),
                            "Poids" to poidsMap.values.toList(),
                        ),
                    )
                }
                Row (modifier = Modifier.height(300.dp).padding(16.dp)) {
                    Chart(
                        data = mapOf(
                            "Poitrine" to poitrineMap.values.toList(),
                            "Taille" to tailleMap.values.toList(),
                            "Hanches" to hanchesMap.values.toList(),
                            "Cuisses" to cuissesMap.values.toList(),
                        ),
                    )
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            Accueil(modifier = Modifier.fillMaxSize(), context = LocalContext.current)
        }
        composable("menu") {
            MainMenuScreen(navController = navController, modifier = Modifier.fillMaxSize())
        }
        composable(
            "profile",
            arguments = emptyList()
        ) {
            val context = LocalContext.current
            ProfileScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                context = context
            )
        }
        composable(
            "weight",
            arguments = emptyList()
        ) {
            WeightScreen(
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            )
        }
        composable(
            "measurements",
            arguments = emptyList()
        ) {
            MeasurementsScreen(
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    navController: NavController
) {
    TopAppBar(
        title = {
            Text(
                text="Mensuivi",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = Inter,
                fontWeight = FontWeight.Black
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { navController.navigate("menu") },
                tint = Color.Black,
                )
        },
        actions = {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Profil",
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .clickable { /* TODO */ }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = White
        ),
    )
}

@Composable
fun StatCarousel(data: Map<String, String>) {
    LazyRow (modifier = Modifier.padding(16.dp)) {
        items (data.size) { index ->
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .border(1.dp, Color(0xFFE0E0E0), AbsoluteRoundedCornerShape(10.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = data.keys.elementAt(index),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(width = 200.dp, height = 50.dp),
                    fontFamily = Inter,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                )
                Text(
                    text = data.values.elementAt(index),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(width = 200.dp, height = 50.dp),
                    fontFamily = Inter,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chart(data: Map<String, List<Any>>) {
    val colors = listOf(
        SolidColor(Color(0xFFF44336)),
        SolidColor(Color(0xFF3F51B5)),
        SolidColor(Color(0xFFFFC107)),
        SolidColor(Color(0xFF4CAF50)),
        SolidColor(Color(0xFF9C27B0)),
        SolidColor(Color(0xFF2196F3)),
        SolidColor(Color(0xFFFF9800)),
        SolidColor(Color(0xFF607D8B)),
    )
    val lines = data.keys.mapNotNull { dataKey ->
        val values = data[dataKey]?.map { it.toString().toDouble() } ?: emptyList()
        if (values.isNotEmpty()) {
            Line(
                label = dataKey,
                values = values,
                color = colors[data.keys.indexOf(dataKey)],
                strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                gradientAnimationDelay = 1000,
                drawStyle = DrawStyle.Stroke(width = 2.dp),
            )
        } else null
    }
    if (lines.isNotEmpty()) {
        LineChart(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFFE0E0E0), AbsoluteRoundedCornerShape(10.dp))
                    .padding(20.dp),
            data = remember { lines },
            curvedEdges = false,
            animationMode = AnimationMode.Together(delayBuilder = {
                it * 500L
            }),
            gridProperties = GridProperties(
                enabled = true,
                xAxisProperties = GridProperties.AxisProperties(enabled = false),
                yAxisProperties = GridProperties.AxisProperties(enabled = false),
            ),
        )
    } else {
        Text(
            text = "Aucune donnée à afficher.",
            modifier = Modifier.padding(16.dp)
        )
    }
}