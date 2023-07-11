package com.uce.edu.data.entity.marvel.characters

import com.uce.edu.logic.data.MarvelChars

data class Result(
    val comics: Comics,
    val description: String,
    val events: Events,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val urls: List<Url>
)

fun Result.getMarvelChars():MarvelChars{
    var comic :String ="No available"
    if(comics.items.isNotEmpty()){
        comic = comics.items[0].name
    }

    val a = MarvelChars(
        id,name,comic,
        thumbnail.path+"."+thumbnail.extension
    )
    return a
}