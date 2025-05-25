package com.example.mensurationsimc.database

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update

@Entity
data class Profile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val lastname: String,
    val height: Int,
)

@Entity
data class Measurement(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val chest: Float,
    val hips: Float,
    val thighs: Float,
    val waist: Float,
    val date: String, // Date in format "DD/MM/YYYY"
)

@Entity
data class WeightBmi(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val weight: Float,
    val bmi: Float,
    val date: String, // Date in format "DD/MM/YYYY"
)

@Dao
interface ProfileDao {
    @Query("SELECT * FROM Profile")
    suspend fun getAll(): List<Profile>

    @Insert
    suspend fun insert(profile: Profile)

    @Update
    suspend fun update(profile: Profile)

    @Delete
    suspend fun delete(profile: Profile)

}

@Dao
interface MeasurementDao {
    @Query("SELECT * FROM Measurement")
    suspend fun getAll(): List<Measurement>

    @Insert
    suspend fun insert(Measurement: Measurement)

    @Delete
    suspend fun delete(Measurement: Measurement)
}

@Dao
interface WeightBmiDao {
    @Query("SELECT * FROM WeightBmi")
    suspend fun getAll(): List<WeightBmi>

    @Insert
    suspend fun insert(WeightBmi: WeightBmi)

    @Delete
    suspend fun delete(WeightBmi: WeightBmi)
}

@Database(entities = [Profile::class, Measurement::class, WeightBmi::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun measurementDao(): MeasurementDao
    abstract fun weightBmiDao(): WeightBmiDao
}