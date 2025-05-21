package com.example.mensurationsimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.mensurationsimc.database.AppDatabase
import com.example.mensurationsimc.database.Profile
import com.example.mensurationsimc.ui.theme.MensurationsIMCTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "profile-database"
        )
            .fallbackToDestructiveMigration(true)
            .build()

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val dbDAO = db.profileDao()
                dbDAO.insert(Profile(name = "John Doe", height = 1.75f))
            }

            val profiles = withContext(Dispatchers.IO) {
                db.profileDao().getAll()
            }

            profiles.forEach {
                println("Profile: ${it.name}, Height: ${it.height}")
            }
        }

        enableEdgeToEdge()
        setContent {
            MensurationsIMCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android ",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MensurationsIMCTheme {
        Greeting("Android")
    }
}
