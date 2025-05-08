package com.example.mensurationsimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mensurationsimc.ui.theme.MensurationsIMCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MensurationsIMCTheme(darkTheme = false) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    HomePage(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
//    onMenuClick: () -> Unit,
//    onProfileClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text="Mensuivi",
                modifier = Modifier,
                textAlign = TextAlign.Center,
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { //onMenuClick()
                    },
                tint = Color.White
                )
        },
        actions = {
//            Image(
//                painter = painterResource(id = R.drawable.icon_profile),
//            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
            )
    )
}

@Preview(showBackground = true)
@Composable
fun HomePage(modifier: Modifier = Modifier) {
    Header()
    Text(
        text = "Home Page",
        modifier = modifier
    )
}