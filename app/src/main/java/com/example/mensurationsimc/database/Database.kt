package com.example.mensurationsimc.database

import androidx.room.Dao
import androidx.room.Database
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

    @Query("SELECT height FROM Profile")
    suspend fun getHeight(): Int?

    @Insert
    suspend fun insert(profile: Profile)

    @Update
    suspend fun update(profile: Profile)

    @Query("DELETE FROM Profile")
    suspend fun clearAll()

}

@Dao
interface MeasurementDao {
    @Query("SELECT * FROM Measurement")
    suspend fun getAll(): List<Measurement>

    @Insert
    suspend fun insert(Measurement: Measurement)

    @Query("DELETE FROM Measurement")
    suspend fun clearAll()
}

@Dao
interface WeightBmiDao {
    @Query("SELECT * FROM WeightBmi")
    suspend fun getAll(): List<WeightBmi>

    @Insert
    suspend fun insert(WeightBmi: WeightBmi)

    @Query("DELETE FROM WeightBmi")
    suspend fun clearAll()
}

@Database(entities = [Profile::class, Measurement::class, WeightBmi::class], version = 6)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun measurementDao(): MeasurementDao
    abstract fun weightBmiDao(): WeightBmiDao
}