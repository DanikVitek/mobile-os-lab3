package me.danikvitek.lab3.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.Date

@Serializable
@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(defaultValue = "")
    var surname: String,

    var name: String,

    @ColumnInfo(defaultValue = "")
    var patronymic: String,

    @ColumnInfo(
        name = "created_at",
        defaultValue = "CURRENT_TIMESTAMP"
    )
    @Serializable(with = DateSerializer::class)
    val createdAt: Date = Date(),
) {
    val fullName: String get() = "$surname $name $patronymic"
}

private class DateSerializer : KSerializer<Date> {
    override val descriptor: SerialDescriptor = serialDescriptor<Long>()

    override fun deserialize(decoder: Decoder): Date = Date(decoder.decodeLong())

    override fun serialize(encoder: Encoder, value: Date) = encoder.encodeLong(value.time)
}
