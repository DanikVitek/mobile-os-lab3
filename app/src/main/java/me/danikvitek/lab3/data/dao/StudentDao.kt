package me.danikvitek.lab3.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import me.danikvitek.lab3.data.entity.Student
import java.util.Date

@Dao
interface StudentDao {
    @Query("SELECT * FROM students")
    fun getAll(): Flow<List<Student>>

    @Query("SELECT * FROM students ORDER BY id DESC LIMIT 1")
    suspend fun getLastAdded(): Student?

    @Query(
        "INSERT INTO students (surname, name, patronymic, created_at) " +
        "VALUES (:surname, :name, :patronymic, :createdAt)"
    )
    suspend fun insert(
        surname: String,
        name: String,
        patronymic: String,
        createdAt: Date = Date(),
    )

    @Insert
    suspend fun insertAll(students: List<Student>)

    @Update
    suspend fun update(student: Student)

    @Query("DELETE FROM students")
    suspend fun deleteAll()

    @Query("DELETE FROM sqlite_sequence WHERE name='students'")
    suspend fun resetAutoincrement()
}