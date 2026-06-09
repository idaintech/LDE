package com.ida.locde

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class EntityExample(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String,
    val phoneNumber: String
)
//Tablica korinisine keledi
