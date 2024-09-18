package me.danikvitek.lab3.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ListenableWorker.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import me.danikvitek.lab3.data.AppDatabase
import me.danikvitek.lab3.data.dao.StudentDao
import me.danikvitek.lab3.data.entity.Student
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    private val studentDao: StudentDao,
    @ApplicationContext applicationContext: Context,
) : ViewModel() {


    init {
        viewModelScope.launch { reseedDatabase(applicationContext) }
    }

    fun addStudent(fullName: String) = viewModelScope.launch {
        studentDao.insert(fullName)
    }

    fun swapLastStudent() = viewModelScope.launch {
        val lastStudent = studentDao.getLastAdded().first() ?: return@launch
        studentDao.update(lastStudent.apply {
            fullName = "Петренко Петро Петрович"
        })
    }

    companion object {
        private const val STUDENT_DATA_FILENAME = "students.json"

        @OptIn(ExperimentalSerializationApi::class)
        private suspend fun reseedDatabase(applicationContext: Context) {
            applicationContext.assets.open(STUDENT_DATA_FILENAME).use { inputStream ->
                Json.decodeFromStream<List<Student>>(inputStream).let { students ->
                    val database = AppDatabase.getInstance(applicationContext)

                    val studentDao = database.studentDao()
                    studentDao.deleteAll()
                    studentDao.resetAutoincrement()
                    studentDao.upsertAll(students)

                    Result.success()
                }
            }
        }
    }
}