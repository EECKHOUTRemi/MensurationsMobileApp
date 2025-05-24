package com.example.mensurationsimc

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

class MenuActivity : ComponentActivity() {
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
                    BaseHeader(
                        menuOnClick = { navController.popBackStack() },
                        profilOnClick = { navController.navigate("profile") }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .padding(horizontal = 20.dp),
    ) {
    }

    // Menu central
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(50.dp),
        modifier = Modifier.padding(top = 150.dp, start = 80.dp)

    ) {
        MenuItem(navController, "Accueil", route = "home")
        MenuItem(navController, "Votre profil", route = "profile")
        MenuItem(navController, "Votre IMC", route = "bmi")
        MenuItem(navController, "Votre poids", route = "weight")
        MenuItem(navController, "Vos mensurations", route = "measurements")
    }
}

@Composable
fun MenuItem(navController: NavController, text: String, route: String) {
    Text(
        text = text,
        fontSize = 24.sp,
        modifier = Modifier
            .clickable { navController.navigate(route.toString()) }
    )
}