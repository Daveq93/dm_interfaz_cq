package com.uce.edu.data.dao.marvel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.uce.edu.data.entity.marvel.characters.database.MarvelCharsDB

@Dao
interface MarvelCharsDao {
    @Query("select * from MarvelCharsDB")
    fun getAllCharacters(): List<MarvelCharsDB>

    @Query("select * from MarvelCharsDB where id=:identificador ")
    fun getOneCharacter(identificador: Int): MarvelCharsDB

    @Insert()
    fun insertMarvelChar(ch: List<MarvelCharsDB>)
}