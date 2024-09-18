package me.danikvitek.lab3.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import androidx.sqlite.db.SupportSQLiteDatabase
import me.danikvitek.lab3.data.dao.StudentDao
import me.danikvitek.lab3.data.entity.Student

@Database(
    entities = [Student::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = AppDatabase.AutoMigration::class),
    ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao

    @RenameColumn(
        tableName = "students",
        fromColumnName = "full_name",
        toColumnName = "name",
    )
    class AutoMigration : AutoMigrationSpec {
        override fun onPostMigrate(db: SupportSQLiteDatabase) {
            db.query("SELECT id, name FROM students").use { cursor ->
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndex("id"))
                    val fullName = cursor.getString(cursor.getColumnIndex("name"))
                    val (surname, name, patronymic) = fullName.split(" ")
                    db.execSQL(
                        "UPDATE students SET surname = ?, name = ?, patronymic = ? WHERE id = ?",
                        arrayOf(
                            surname,
                            name,
                            patronymic,
                            id,
                        )
                    )
                }
            }
        }
    }

    companion object {
        private const val DATABASE_NAME = "mobile-os-lab3"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }
}