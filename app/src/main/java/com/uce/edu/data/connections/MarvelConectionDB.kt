package com.uce.edu.data.connections

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uce.edu.data.dao.marvel.MarvelCharsDao
import com.uce.edu.data.entity.marvel.characters.database.MarvelCharsDB

@Database(
    entities = [MarvelCharsDB::class],
    version = 1
)
abstract class MarvelConectionDB:RoomDatabase() {

    abstract fun marvelDato():MarvelCharsDao

}