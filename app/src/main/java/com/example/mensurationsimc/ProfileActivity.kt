package com.example.mensurationsimc

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.mensurationsimc.database.AppDatabase
import com.example.mensurationsimc.database.WeightBmi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfilActivity : ComponentActivity() {
    val tag = "MENSUIVI"
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.Transparent.toArgb(), White.toArgb())
        )
        setContent {
            val navController = rememberNavController()
            Scaffold(
                topBar = {
                    ProfileScreen()
                    Header(navController = navController)
                },
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                RootNavHost(
                    navController = navController,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                )
                ProfileScreen(modifier = Modifier.padding(innerPadding))
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

fun BuildDb(context: Context): Unit {
    var poidsImc: List<WeightBmi> = emptyList()
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "mensuivi_db"
    ).build()

    Thread {
        CoroutineScope(Dispatchers.IO).launch {
            poidsImc = db.weightBmiDao().getAll()

        }
    }.start()

    Log.i("MENSUIVI", poidsImc.toString())
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Votre Profil",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 48.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Photo de profil",
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(25.dp))
            Column {
                Text("Clémence", fontSize = 20.sp)
                Text("Dupont", fontSize = 20.sp)
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            val context = LocalContext.current
            Text("Votre poids actuel :", fontSize = 18.sp)
            Text("Votre IMC :", fontSize = 18.sp)
            Text("Votre taille :", fontSize = 18.sp)
            Text("Vos mensurations :", fontSize = 18.sp)
            Button(
                onClick = { BuildDb(context) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Modifier le profil")
            }
        }
    }
}