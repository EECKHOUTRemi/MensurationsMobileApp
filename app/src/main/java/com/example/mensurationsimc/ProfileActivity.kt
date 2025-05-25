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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.mensurationsimc.database.AppDatabase
import com.example.mensurationsimc.database.Profile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileActivity : ComponentActivity() {
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
@Composable
fun ProfileScreen(modifier: Modifier = Modifier, context: Context) {
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "mensuivi_db"
    ).fallbackToDestructiveMigration().build()

    val profileDao = db.profileDao()

    // Champs modifiables
    var name by remember { mutableStateOf("Clémence") }
    var lastname by remember { mutableStateOf("Dupont") }
    var height by remember { mutableStateOf("150") }
//    var measurement by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val profiles = profileDao.getAll()
        if (profiles.isNotEmpty()) {
            val profile = profiles.last()
            name = profile.name
            lastname = profile.lastname
            height = profile.height.toString()
        }
    }

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
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Prénom") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = lastname,
                    onValueChange = { lastname = it },
                    label = { Text("Nom") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                label = { Text("Taille (cm)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
        val context = LocalContext.current
        Button(
            onClick = {
                message = "Profil enregistré avec succès !"
                SendDataProfile(context, name, lastname, height)
                name = ""; lastname = ""; height = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enregistrer")
        }

        if (message.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                color = Color(0xFF2E7D32), // vert
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun SendDataProfile(
    context: Context,
    name: String,
    lastname: String,
    height: String
) {

    try {
        val nameValue = name
        val lastnameValue = lastname
        val heightValue = height.toInt()

        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "mensuivi_db"
        ).build()

        val profile = Profile(
            name = nameValue,
            lastname = lastnameValue,
            height = heightValue
        )

        Thread {
            CoroutineScope(Dispatchers.IO).launch {
                if (db.profileDao().getAll().isEmpty()) {
                    db.profileDao().insert(profile)
                } else {
                    db.profileDao().update(profile)
                }
            }
        }.start()

        Log.i("MENSUIVI", "Measurements sent: Name=$nameValue, Lastname=$lastnameValue, Height=$heightValue")
        Toast.makeText(context, "Données envoyées avec succès", Toast.LENGTH_SHORT).show()
    } catch (e: NumberFormatException) {
        Log.e("MENSUIVI", "Invalid number format", e)
        Toast.makeText(context, "Veuillez entrer des valeurs numériques valides", Toast.LENGTH_SHORT).show()
    }
}