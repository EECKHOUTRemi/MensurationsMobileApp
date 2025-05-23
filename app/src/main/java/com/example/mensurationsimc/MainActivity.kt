package com.example.mensurationsimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.mensurationsimc.database.AppDatabase
import com.example.mensurationsimc.database.Profile
import com.example.mensurationsimc.ui.theme.MensurationsIMCTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        lifecycleScope.launch {
            val db = withContext(Dispatchers.IO) {
                Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java,
                    "profile-database"
                )
                    .fallbackToDestructiveMigration(true)
                    .allowMainThreadQueries()
                    .build()
            }

            setContent {
                val viewModel = ProfileViewModel(db)
                MensurationsIMCTheme {
                    ProfileScreen(viewModel = viewModel)
                }
            }
        }
    }
}

class ProfileViewModel(database: AppDatabase) : ViewModel() {
    private val profileDao = database.profileDao()

    private val _profiles = MutableStateFlow<List<Profile>>(emptyList())
    val profiles: StateFlow<List<Profile>> = _profiles

    init {
        loadProfiles()
    }

    private fun loadProfiles() {
        viewModelScope.launch {
            _profiles.value = profileDao.getAll()
        }
    }
}

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val profiles by viewModel.profiles.collectAsState()

    profiles.forEach { profile ->
        Text(text = "Nom: ${profile.name}, Taille: ${profile.height}")
    }
}
