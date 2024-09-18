package me.danikvitek.lab3.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import me.danikvitek.lab3.data.AppDatabase
import me.danikvitek.lab3.data.entity.Student

/**
 * Fills the database with the data from the JSON file
 */
class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {
    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val filename = inputData.getString(KEY_FILENAME)
            if (filename == null) {
                Log.e(TAG, "Error seeding database - no valid filename")
                return@withContext Result.failure()
            }

            applicationContext.assets.open(filename).use { inputStream ->
                Json.decodeFromStream<List<Student>>(inputStream).let { students ->
                    val database = AppDatabase.getInstance(applicationContext)

                    database.studentDao().upsertAll(students)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private val TAG = SeedDatabaseWorker::class.simpleName!!
        const val KEY_FILENAME = "STUDENT_DATA_FILENAME"
    }
}