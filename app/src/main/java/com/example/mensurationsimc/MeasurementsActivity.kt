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
import com.example.mensurationsimc.database.Measurement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class MeasurementsActivity : ComponentActivity() {
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
fun SendDataMeasurements(
    context: Context,
    chest: String,
    hips: String,
    thighs: String,
    waist: String,
    date: String? = null
) {
    val dateValue = date ?: LocalDate.now().toString()

    if (chest.isEmpty() || hips.isEmpty() || thighs.isEmpty() || waist.isEmpty()) {
        Log.e("MENSUIVI", "All fields must be filled")
        Toast.makeText(context, "Les champs marqués d'une astérisque doivent être complétés", Toast.LENGTH_SHORT).show()
        return
    }

    try {
        val chestValue = chest.toFloat()
        val hipsValue = hips.toFloat()
        val thighsValue = thighs.toFloat()
        val waistValue = waist.toFloat()

        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "mensuivi_db"
        ).build()

        val measurement = Measurement(
            chest = chestValue,
            hips = hipsValue,
            thighs = thighsValue,
            waist = waistValue,
            date = dateValue
        )

        Thread {
            CoroutineScope(Dispatchers.IO).launch {
                db.measurementDao().insert(measurement)
            }
        }.start()

        Log.i("MENSUIVI", "Measurements sent: Chest=$chestValue, Hips=$hipsValue, Thighs=$thighsValue, Waist=$waistValue, Date=$dateValue")
        Toast.makeText(context, "Données envoyées avec succès", Toast.LENGTH_SHORT).show()
    } catch (e: NumberFormatException) {
        Log.e("MENSUIVI", "Invalid number format", e)
        Toast.makeText(context, "Veuillez entrer des valeurs numériques valides", Toast.LENGTH_SHORT).show()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MeasurementsForm() {
    var chest by remember { mutableStateOf("") }
    var hips by remember { mutableStateOf("") }
    var thighs by remember { mutableStateOf("") }
    var waist by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(10.dp))
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(24.dp)
    ) {
        // Poitrine
        Text("Poitrine *", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        OutlinedTextField(
            value = chest,
            onValueChange = { chest = it },
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

        // Taille
        Text("Taille *", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        OutlinedTextField(
            value = waist,
            onValueChange = { waist = it },
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

        // Hanches
        Text("Hanches *", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        OutlinedTextField(
            value = hips,
            onValueChange = { hips = it },
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

        // Cuisses
        Text("Cuisses *", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        OutlinedTextField(
            value = thighs,
            onValueChange = { thighs = it },
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
                SendDataMeasurements(context, chest, hips, thighs, waist, date)
                chest = ""; hips = ""; thighs = ""; waist = ""; date = ""
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
fun MeasurementsScreen(navController: NavController, modifier: Modifier) {
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
                text = "Vos mensurations",
                modifier = modifier
                    .padding(top = 10.dp ,bottom = 30.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
            )
        }
        MeasurementsForm()
    }
}