package com.example.mensurationsimc

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
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.toArgb
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
import com.example.mensurationsimc.ui.theme.Inter
import com.example.mensurationsimc.ui.theme.MensurationsIMCTheme
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.Line

class MainActivity : ComponentActivity() {
    val tag = "MENSUIVI"
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
                RootNavHost(
                    navController = navController,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                )
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
fun Accueil(modifier: Modifier) {
    val taille = 1.55f
    val poidsIMC = mapOf(
        "IMC" to mapOf(
            "07/11/2023" to 89.4 / (taille * taille),
            "11/11/2023" to 88.2 / (taille * taille),
            "18/11/2023" to 87.2 / (taille * taille),
            "25/11/2023" to 86.7 / (taille * taille),
            "02/12/2023" to 85.6 / (taille * taille),
            "09/12/2023" to 84.5 / (taille * taille),
            "16/12/2023" to 83.6 / (taille * taille),
            "23/12/2023" to 83.5 / (taille * taille),
            "30/12/2023" to 82.9 / (taille * taille),
            "06/01/2024" to 82.8 / (taille * taille),
            "13/01/2024" to 81.7 / (taille * taille),
            "20/01/2024" to 81.5 / (taille * taille),
            "27/01/2024" to 81.0 / (taille * taille),
            "03/02/2024" to 80.9 / (taille * taille),
            "10/02/2024" to 80.5 / (taille * taille),
            "17/02/2024" to 80.5 / (taille * taille),
            "24/02/2024" to 80.4 / (taille * taille),
            "02/03/2024" to 80.2 / (taille * taille),
            "09/03/2024" to 79.4 / (taille * taille),
            "16/03/2024" to 79.0 / (taille * taille),
            "23/03/2024" to 78.9 / (taille * taille),
            "30/03/2024" to 78.5 / (taille * taille),
            "06/04/2024" to 78.4 / (taille * taille),
            "13/04/2024" to 78.4 / (taille * taille),
            "20/04/2024" to 78.2 / (taille * taille),
            "27/04/2024" to 77.9 / (taille * taille),
            "11/05/2024" to 77.9 / (taille * taille),
            "18/05/2024" to 77.9 / (taille * taille),
            "25/05/2024" to 77.8 / (taille * taille),
            "01/06/2024" to 77.6 / (taille * taille),
            "12/06/2024" to 77.5 / (taille * taille),
            "15/06/2024" to 77.3 / (taille * taille),
            "21/06/2024" to 77.1 / (taille * taille),
            "28/06/2024" to 77.1 / (taille * taille),
            "06/07/2024" to 76.9 / (taille * taille),
            "19/07/2024" to 76.8 / (taille * taille),
            "26/07/2024" to 76.8 / (taille * taille),
            "02/08/2024" to 76.2 / (taille * taille),
            "16/08/2024" to 70.8 / (taille * taille),
            "23/08/2024" to 69.5 / (taille * taille),
            "30/08/2024" to 69.1 / (taille * taille),
            "06/09/2024" to 68.7 / (taille * taille),
            "13/09/2024" to 67.6 / (taille * taille),
            "20/09/2024" to 67.2 / (taille * taille),
            "27/09/2024" to 66.9 / (taille * taille),
            "07/10/2024" to 66.1 / (taille * taille),
            "11/10/2024" to 65.9 / (taille * taille),
            "18/10/2024" to 65.6 / (taille * taille),
            "25/10/2024" to 64.6 / (taille * taille),
            "01/11/2024" to 63.0 / (taille * taille),
            "31/12/2024" to 62.8 / (taille * taille),
            "10/01/2025" to 62.4 / (taille * taille),
            "17/01/2025" to 61.7 / (taille * taille),
            "24/01/2025" to 61.4 / (taille * taille),
            "30/01/2025" to 62.0 / (taille * taille),
            "07/02/2025" to 61.0 / (taille * taille)
        ),
        "Poids" to mapOf(
            "07/11/2023" to 89.4,
            "11/11/2023" to 88.2,
            "18/11/2023" to 87.2,
            "25/11/2023" to 86.7,
            "02/12/2023" to 85.6,
            "09/12/2023" to 84.5,
            "16/12/2023" to 83.6,
            "23/12/2023" to 83.5,
            "30/12/2023" to 82.9,
            "06/01/2024" to 82.8,
            "13/01/2024" to 81.7,
            "20/01/2024" to 81.5,
            "27/01/2024" to 81.0,
            "03/02/2024" to 80.9,
            "10/02/2024" to 80.5,
            "17/02/2024" to 80.5,
            "24/02/2024" to 80.4,
            "02/03/2024" to 80.2,
            "09/03/2024" to 79.4,
            "16/03/2024" to 79.0,
            "23/03/2024" to 78.9,
            "30/03/2024" to 78.5,
            "06/04/2024" to 78.4,
            "13/04/2024" to 78.4,
            "20/04/2024" to 78.2,
            "27/04/2024" to 77.9,
            "11/05/2024" to 77.9,
            "18/05/2024" to 77.9,
            "25/05/2024" to 77.8,
            "01/06/2024" to 77.6,
            "12/06/2024" to 77.5,
            "15/06/2024" to 77.3,
            "21/06/2024" to 77.1,
            "28/06/2024" to 77.1,
            "06/07/2024" to 76.9,
            "19/07/2024" to 76.8,
            "26/07/2024" to 76.8,
            "02/08/2024" to 76.2,
            "16/08/2024" to 70.8,
            "23/08/2024" to 69.5,
            "30/08/2024" to 69.1,
            "06/09/2024" to 68.7,
            "13/09/2024" to 67.6,
            "20/09/2024" to 67.2,
            "27/09/2024" to 66.9,
            "07/10/2024" to 66.1,
            "11/10/2024" to 65.9,
            "18/10/2024" to 65.6,
            "25/10/2024" to 64.6,
            "01/11/2024" to 63.0,
            "31/12/2024" to 62.8,
            "10/01/2025" to 62.4,
            "17/01/2025" to 61.7,
            "24/01/2025" to 61.4,
            "30/01/2025" to 62.0,
            "07/02/2025" to 61.0
        )
    )
    val mensurations = mapOf(
        "Poitrine" to mapOf(
            "01/11/2024" to 54.0,
            "20/12/2024" to 53.0,
            "11/01/2025" to 53.0,
            "17/03/2025" to 53.0,
            "08/04/2025" to 52.5
        ),
        "Taille" to mapOf(
            "01/11/2024" to 115.0,
            "20/12/2024" to 112.0,
            "11/01/2025" to 108.0,
            "17/03/2025" to 104.0,
            "08/04/2025" to 103.0
        ),
        "Hanches" to mapOf(
            "01/11/2024" to 101.0,
            "20/12/2024" to 96.0,
            "11/01/2025" to 92.0,
            "17/03/2025" to 89.0,
            "08/04/2025" to 88.0
        ),
        "Cuisses" to mapOf(
            "01/11/2024" to 118.0,
            "20/12/2024" to 114.0,
            "11/01/2025" to 109.5,
            "17/03/2025" to 104.0,
            "08/04/2025" to 103.0
        )
    )
    val lastWeight = poidsIMC["Poids"]!!.values.last()
    MensurationsIMCTheme(darkTheme = false) {
        Column(modifier = modifier) {
            Row {
                StatCarousel(
                    data = mapOf(
                        "Dernier poids en registré" to "$lastWeight kg",
                        "Taille actuelle" to "$taille m",
                        "Dernier IMC calculé" to poidsIMC["IMC"]!!.values.last().toString(),
                    ),
                )
            }
            Row (modifier = Modifier
                .height(300.dp)
                .padding(16.dp)
            ) {
                Chart(
                    data = mapOf(
                        "IMC" to poidsIMC["IMC"]!!.values.toList(),
                        "Poids" to poidsIMC["Poids"]!!.values.toList(),
                    ),
                )
            }
            Row (modifier = Modifier
                .height(300.dp)
                .padding(16.dp)
            ) {
                Chart(
                    data = mapOf(
                        "Poitrine" to mensurations["Poitrine"]!!.values.toList(),
                        "Taille" to mensurations["Taille"]!!.values.toList(),
                        "Hanches" to mensurations["Hanches"]!!.values.toList(),
                        "Cuisses" to mensurations["Cuisses"]!!.values.toList(),
                    ),
                )
            }
        }
    }
}

@Composable
fun RootNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            Accueil(modifier = Modifier.fillMaxSize())
        }
        composable("menu") {
            MainMenuScreen(navController = navController, modifier = Modifier.fillMaxSize())
        }
        composable(
            "profile",
            arguments = emptyList()
        ) {
            ProfileScreen(
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            )
        }
    }
}

@Composable
fun ProfileScreen(navController: NavController, modifier: Modifier) {
    TODO("Not yet implemented")
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
    LazyRow (modifier = Modifier) {
        items (data.size) { index ->
            Column(
                modifier = Modifier
                    .padding(16.dp)
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
                    text = data.values.elementAt(index).toString(),
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

@Composable
fun Chart(data: Map<String, List<Any>>) {
    var colors = listOf(
        SolidColor(Color(0xFFF44336)),
        SolidColor(Color(0xFF3F51B5)),
        SolidColor(Color(0xFFFFC107)),
        SolidColor(Color(0xFF4CAF50)),
        SolidColor(Color(0xFF9C27B0)),
        SolidColor(Color(0xFF2196F3)),
        SolidColor(Color(0xFFFF9800)),
        SolidColor(Color(0xFF607D8B)),
    )
    val lines = data.keys.map { dataKey ->
        Line(
            label = dataKey,
            values = data[dataKey]!!.map { it.toString().toDouble() },
            color = colors[data.keys.indexOf(dataKey)],
            strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
            gradientAnimationDelay = 1000,
            drawStyle = DrawStyle.Stroke(width = 2.dp),
        )
    }
    val minValue = data.values.flatten().filterIsInstance<Number>().minOfOrNull { it.toDouble() } ?: 0.0
    val maxValue = data.values.flatten().filterIsInstance<Number>().maxOfOrNull { it.toDouble() } ?: 100.0
    LineChart(modifier =
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
        minValue = (minValue - minValue/10).toInt().toDouble(),
        maxValue = (maxValue + maxValue/20).toInt().toDouble(),
    )
}