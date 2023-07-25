package com.uce.edu.data.entity.marvel.characters.database.data

import android.os.Parcelable
import com.uce.edu.data.entity.marvel.characters.database.MarvelCharsDB
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarvelChars(val id: Int,
                       val name: String,
                       val comic: String,
                       val image: String
                       ) :Parcelable

fun MarvelChars.getMarvelCharsDB():MarvelCharsDB{
    return MarvelCharsDB(
        id,name,comic,image
    )
}