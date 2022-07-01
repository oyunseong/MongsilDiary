package com.mongsil.mongsildiary.data.database

import androidx.room.Database
import androidx.room.TypeConverters
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mongsil.mongsildiary.data.database.entity.RecordEntity
import com.mongsil.mongsildiary.data.database.entity.SlotEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.chrono.HijrahChronology.INSTANCE


@Database(entities = [SlotEntity::class, RecordEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao

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

//    private class AppDataBaseCallback(
//        private val scope: CoroutineScope
//    ) : RoomDatabase.Callback() {
//        override fun onCreate(db: SupportSQLiteDatabase) {
//
//            override fun onCreate(db: SupportSQLiteDatabase) {
//                super.onCreate(db)
//                INSTANCE?.let { database ->
//                    scope.launch {
//                        var dao = database.
//
//                    }
//                }
//            }
//        }
//    }
}