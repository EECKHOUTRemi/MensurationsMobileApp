package com.example.mensurationsimc.database

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity
data class Profile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val height: Float, //Height in meters
)

@Entity
data class MeasurementWeight(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val poids: Float,
    val imc: Float
)

@Dao
interface ProfileDao {
    @Query("SELECT * FROM Profile")
    fun getAll(): List<Profile>

    @Insert
    fun insert(profile: Profile)

    @Delete
    fun delete(profile: Profile)
}

@Dao
interface MeasurementWeightDao {
    @Query("SELECT * FROM MeasurementWeight")
    suspend fun getAll(): List<MeasurementWeight>

    @Insert
    suspend fun insert(measurementWeight: MeasurementWeight)

    @Delete
    suspend fun delete(measurementWeight: MeasurementWeight)
}

@Database(entities = [Profile::class, MeasurementWeight::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun measurementWeightDao(): MeasurementWeightDao
}