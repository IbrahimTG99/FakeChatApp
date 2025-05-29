package com.readychatai.dev.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.readychatai.dev.data.room.converters.Converters

@Entity
@TypeConverters(Converters::class)
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val keywords: List<String>
)
