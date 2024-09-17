package me.danikvitek.lab3.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import me.danikvitek.lab3.data.entity.Student
import java.util.Date

@Dao
interface StudentDao {
    @Query("SELECT * FROM students")
    fun getAll(): Flow<List<Student>>

    @Query("SELECT * FROM students ORDER BY id DESC LIMIT 1")
    fun getLastAdded(): Flow<Student?>

    @Query("INSERT INTO students (full_name, created_at) VALUES (:fullName, :createdAt)")
    suspend fun insert(fullName: String, createdAt: Date = Date())

    suspend fun insert(vararg fullNames: String) {
        for (fullName in fullNames) insert(fullName)
    }

    @Upsert
    suspend fun upsertAll(students: List<Student>)

    @Update
    suspend fun update(student: Student)

    @Delete
    suspend fun delete(student: Student)

    @Query("DELETE FROM students")
    suspend fun deleteAll()

    @Query("DELETE FROM sqlite_sequence WHERE name='students'")
    suspend fun resetAutoincrement()
}