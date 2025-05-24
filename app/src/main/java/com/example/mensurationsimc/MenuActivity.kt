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
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MenuActivity : ComponentActivity() {
    val tag = "MENSUIVI"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.Transparent.toArgb(), White.toArgb()),
        )
        setContent {
            Scaffold(
                topBar = {
                    MainMenuScreen()
                    Header(
                        onMenuClick = { /*  */ },
                        onProfileClick = { /*  */ }
                    )
                },
                modifier = Modifier
                    .fillMaxSize(),
            ) { innerPadding ->
                Column {
                    Text(
                        text = "Main Menu",
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxWidth()
                            .padding(16.dp),
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(modifier: Modifier = Modifier) {
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
        MenuItem("Accueil")
        MenuItem("Votre profil")
        MenuItem("Votre IMC")
        MenuItem("Votre poids")
        MenuItem("Vos mensurations")
    }
}

@Composable
fun MenuItem(text: String) {
    Text(
        text = text,
        fontSize = 24.sp,
        modifier = Modifier
            .clickable { /* TODO: Navigate */ }
    )
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun Header(
//    onMenuClick: () -> Unit,
//    onProfileClick: () -> Unit,
//) {
//    TopAppBar(
//        title = {
//            Text(
//                text = "Mensuivi",
//                modifier = Modifier.fillMaxWidth(),
//                textAlign = TextAlign.Center,
//                fontFamily = Inter,
//                fontWeight = FontWeight.Black
//            )
//        },
//        navigationIcon = {
//            Icon(
//                imageVector = Icons.Default.Menu,
//                contentDescription = "Menu",
//                modifier = Modifier
//                    .padding(8.dp)
//                    .clickable { onMenuClick() },
//                tint = Color.Black
//            )
//        },
//        actions = {
//            Image(
//                painter = painterResource(id = R.drawable.ic_launcher_background),
//                contentDescription = "Profil",
//                modifier = Modifier
//                    .size(50.dp)
//                    .padding(8.dp)
//                    .clip(CircleShape)
//                    .clickable { onProfileClick() }
//            )
//        },
//        colors = TopAppBarDefaults.topAppBarColors(
//            containerColor = White
//        ),
//    )
//}