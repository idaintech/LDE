package com.ida.locde

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

//DAO Data Access Object

@Dao
interface ExampleDao {

    @Insert  //bazaga magliwmat qosiw
    fun add(exampleData: EntityExample)

    @Delete
    fun delete(data: EntityExample)

    @Query("SELECT * FROM users")
    fun getAllDatas(): List<EntityExample>

    //zapros soraw:  magliwmat aliw
    //SQL tilinde jaziladi
}

/*MVVM - model view view model
MVP - model view presenter - local api */