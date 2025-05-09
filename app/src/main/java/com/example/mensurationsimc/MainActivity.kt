package com.example.mensurationsimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mensurationsimc.ui.theme.Inter
import com.example.mensurationsimc.ui.theme.MensurationsIMCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.Transparent.toArgb(), Color.White.toArgb()),
        )
        setContent {
            MensurationsIMCTheme(darkTheme = false) {
                Scaffold(
                    topBar = {
                        Header(
                            onMenuClick = { /*  */ },
                            onProfileClick = { /*  */ }
                        )
                    },
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        StatCarousel(
                            data = mapOf("Poids" to "70kg", "Taille" to "1.75m", "IMC" to "22.9"),
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit,
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
                    .clickable { onMenuClick() },
                tint = Color.Black
                )
        },
        actions = {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Profil",
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .clickable { onProfileClick() }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = White
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun StatCarousel(data: Map<String, String>, modifier: Modifier) {
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MensurationsIMCTheme {
    }
}