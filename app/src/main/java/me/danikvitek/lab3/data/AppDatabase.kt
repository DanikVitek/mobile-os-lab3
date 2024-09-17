package me.danikvitek.lab3.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.danikvitek.lab3.data.dao.StudentDao
import me.danikvitek.lab3.data.entity.Student

//import androidx.sqlite.db.SupportSQLiteDatabase
//import androidx.work.OneTimeWorkRequestBuilder
//import androidx.work.WorkManager
//import androidx.work.workDataOf
//import me.danikvitek.lab3.worker.SeedDatabaseWorker
//import me.danikvitek.lab3.worker.SeedDatabaseWorker.Companion.KEY_FILENAME

@Database(
    entities = [Student::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao

    companion object {
        private const val DATABASE_NAME = "mobile-os-lab3"
        private const val STUDENT_DATA_FILENAME = "students.json"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
//                .addCallback(object : Callback() {
//                    override fun onCreate(db: SupportSQLiteDatabase) {
//                        super.onCreate(db)
//                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
//                            .setInputData(workDataOf(KEY_FILENAME to STUDENT_DATA_FILENAME))
//                            .build()
//                        WorkManager.getInstance(context).enqueue(request)
//                    }
//                })
                .build()
    }
}