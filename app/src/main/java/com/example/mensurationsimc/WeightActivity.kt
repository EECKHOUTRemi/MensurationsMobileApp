package com.example.mensurationsimc

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.mensurationsimc.database.AppDatabase
import com.example.mensurationsimc.database.WeightBmi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class WeightActivity : ComponentActivity() {
    val tag = "MENSUIVI"
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(tag, "Main activity created")
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.Transparent.toArgb(), Color.White.toArgb()),
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
}



@RequiresApi(Build.VERSION_CODES.O)
fun SendDataWeight(
    context: Context,
    weight: String,
    height: String,
    date: String? = null
) {
    val dateValue = date ?: LocalDate.now().toString()

    if (weight.isEmpty() || height.isEmpty()) {
        Log.e("MENSUIVI", "All fields must be filled")
        Toast.makeText(context, "Les champs marqués d'une astérisque doivent être complétés", Toast.LENGTH_SHORT).show()
        return
    }

    try {
        val weightValue = weight.toFloat()
        val heightValue = height.toInt()
        val bmiValue = if (heightValue > 0) {
            weightValue / ((heightValue / 100) * (heightValue / 100)) // BMI = weight(kg) / (height(m)^2)
        } else {
            0f // Avoid division by zero
        }

        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "mensuivi_db"
        ).build()

        val poids = WeightBmi(
            weight = weightValue,
            bmi = bmiValue,
            date = dateValue
        )

        Thread {
            CoroutineScope(Dispatchers.IO).launch {
                db.weightBmiDao().insert(poids)
                db.profileDao().update(
                    db.profileDao().getAll().lastOrNull()?.copy(
                        height = heightValue
                    ) ?: return@launch
                )
            }
        }.start()

        Log.i("MENSUIVI", "Weight sent: Weight=$weightValue, Height=$heightValue, BMI=$bmiValue, Date=$dateValue")
        Toast.makeText(context, "Données envoyées avec succès", Toast.LENGTH_SHORT).show()
    } catch (e: NumberFormatException) {
        Log.e("MENSUIVI", "Invalid number format", e)
        Toast.makeText(context, "Veuillez entrer des valeurs numériques valides", Toast.LENGTH_SHORT).show()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeightForm(context: Context) {
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "mensuivi_db"
    ).fallbackToDestructiveMigration().build()

    val profileDao = db.profileDao()

    // Champs modifiables
    var height by remember { mutableStateOf("150") }

    LaunchedEffect(Unit) {
        val profiles = profileDao.getAll()
        if (profiles.isNotEmpty()) {
            val profile = profiles.last()
            height = profile.height.toString()
        }
    }

    var weight by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(10.dp))
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(24.dp)
    ) {
        // Poids
        Text("Poids *", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            placeholder = { Text(text = "En kg", color = Color.LightGray) },
            modifier = Modifier
                .padding(top = 4.dp, bottom = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.LightGray,
                unfocusedIndicatorColor = Color.LightGray
            )
        )

        // Taille
        Text("Taille *", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            placeholder = { Text(text = "En cm", color = Color.LightGray) },
            modifier = Modifier
                .padding(top = 4.dp, bottom = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.LightGray,
                unfocusedIndicatorColor = Color.LightGray
            )
        )
        // Date (optionnelle)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Date", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(Modifier.width(8.dp))
            Text("(optionelle)", color = Color.Gray, fontSize = 14.sp)
        }
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            placeholder = { Text(text = "JJ/MM/AAAA", color = Color.LightGray) },
            modifier = Modifier
                .padding(top = 4.dp, bottom = 24.dp),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.LightGray,
                unfocusedIndicatorColor = Color.LightGray
            )
        )

        // Bouton Envoyer
        val context = LocalContext.current
        Button(
            onClick = {
                //changer la database
                SendDataWeight(context, weight, height, date)
                weight = ""; height = ""; date = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D2D2D))
        ) {
            Text("Envoyer", color = Color.White, fontSize = 16.sp)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeightScreen(navController: NavController, modifier: Modifier) {
    val context = LocalContext.current
    Column (
        modifier = modifier.fillMaxSize()
            .padding(20.dp),
    ){
        Row {
            Text(
                text = "Quoi de neuf ?",
                modifier = modifier
                    .padding(top = 30.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black,
                fontSize = 30.sp,
            )
        }
        Row {
            Text(
                text = "Votre poids",
                modifier = modifier
                    .padding(top = 10.dp ,bottom = 30.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
            )
        }
        WeightForm(context)
    }
}