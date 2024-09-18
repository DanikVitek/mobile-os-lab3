package me.danikvitek.lab3.viewmodel

import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import me.danikvitek.lab3.data.dao.StudentDao
import me.danikvitek.lab3.data.entity.Student
import me.danikvitek.lab3.di.WithTransaction
import javax.inject.Inject

@OptIn(ExperimentalSerializationApi::class)
@HiltViewModel
class StartScreenViewModel @Inject constructor(
    private val studentDao: StudentDao,
    private val withTransaction: WithTransaction,
    assets: AssetManager,
) : ViewModel() {

    init {
        viewModelScope.launch {
            assets.open(STUDENT_DATA_FILENAME).use { inputStream ->
                Json.decodeFromStream<List<Student>>(inputStream).let { students ->
                    withTransaction {
                        with(studentDao) {
                            deleteAll()
                            resetAutoincrement()
                            insertAll(students)
                        }
                    }
                }
            }
        }
    }

    fun addStudent(fullName: String) = viewModelScope.launch {
        studentDao.insert(fullName)
    }

    fun swapLastStudent() = viewModelScope.launch {
        withTransaction {
            val lastStudent = studentDao.getLastAdded() ?: return@withTransaction
            studentDao.update(lastStudent.apply {
                fullName = "Петренко Петро Петрович"
            })
        }
    }

    companion object {
        private const val STUDENT_DATA_FILENAME = "students.json"
    }
}