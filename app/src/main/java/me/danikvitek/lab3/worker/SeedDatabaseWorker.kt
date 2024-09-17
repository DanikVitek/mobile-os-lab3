package me.danikvitek.lab3.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.danikvitek.lab3.data.AppDatabase
import me.danikvitek.lab3.data.entity.Student

/**
 * Fills the database with the data from the JSON file
 */
class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val filename = inputData.getString(KEY_FILENAME)
            if (filename == null) {
                Log.e(TAG, "Error seeding database - no valid filename")
                return@withContext Result.failure()
            }

            applicationContext.assets.open(filename).use { inputStream ->
                JsonReader(inputStream.bufferedReader()).use { jsonReader ->
                    val listType = object : TypeToken<List<Student>>() {}.type
                    val students: List<Student> = Gson().fromJson(jsonReader, listType)

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
        private const val TAG = "AppDatabaseWorker"
        const val KEY_FILENAME = "STUDENT_DATA_FILENAME"
    }
}