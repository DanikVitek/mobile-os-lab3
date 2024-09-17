package me.danikvitek.lab3.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import me.danikvitek.lab3.data.dao.StudentDao
import me.danikvitek.lab3.data.entity.Student
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor (
    private val studentDao: StudentDao,
) : ViewModel() {

    init {
        viewModelScope.launch {
            studentDao.deleteAll()
            studentDao.resetAutoincrement()
            studentDao.insert(
                "Іваненко Іван Іванович",
                "Сидоренко Сидір Сидорович",
                "Микитенко Микита Микитович",
                "Олексієнко Олексій Олексійович",
                "Максименко Максим Максимович",
            )
        }
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

    fun deleteStudent(student: Student) = viewModelScope.launch {
        studentDao.delete(student)
    }
}