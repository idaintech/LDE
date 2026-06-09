package com.ida.locde.ui.theme

import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase
import com.ida.locde.EntityExample
import com.ida.locde.ExampleDao

@Database(
    entities = [EntityExample::class],
    version = 1
)
abstract class DatabaseExample: RoomDatabase() {
    abstract  fun getDao(): ExampleDao
}