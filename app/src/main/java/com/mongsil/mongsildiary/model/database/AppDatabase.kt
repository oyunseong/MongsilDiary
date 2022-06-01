package com.mongsil.mongsildiary.model.database

import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.TypeConverters
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mongsil.mongsildiary.model.database.entity.RecordEntity
import com.mongsil.mongsildiary.model.database.entity.SlotEntity


@Database(entities = [SlotEntity::class, RecordEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "MongsilDatabase"

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}