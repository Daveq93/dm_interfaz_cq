package com.uce.edu.logic.marvelLogic

import com.uce.edu.data.connections.ApiConnection
import com.uce.edu.data.endpoint.MarvelEndPoint
import com.uce.edu.data.entity.marvel.characters.getMarvelChars
import com.uce.edu.logic.data.MarvelChars

class MarvelLogic {


    suspend fun getMarvelChars(name: String, limit: Int): List<MarvelChars> {
        var call =
            ApiConnection.getService(ApiConnection.typeApi.Marvel, MarvelEndPoint::class.java)
        // val response = call!!.create(JikanEndPoint::class.java)?.getAllAnimes()

        var itemList = arrayListOf<MarvelChars>()


        if (call != null) {
            var response = call.getCharactersStarWith(name, limit)
            if (response.isSuccessful) {


                if (response != null) {
                    response.body()!!.data.results.forEach {
                        var comic: String = ""
                        if (it.comics.items.size > 0) {
                            comic = it.comics.items[0].name
                        }
                        val m = MarvelChars(
                            it.id,
                            it.name,
                            comic,
                            it.thumbnail.path + "." + it.thumbnail.extension
                        )
                        itemList.add(m)

                    }
                }
            }
        }
        return itemList
    }


    suspend fun getAllMarvelChars(offset: Int, limit: Int): List<MarvelChars> {
        var itemList = arrayListOf<MarvelChars>()
        val response = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndPoint::class.java


        ).getAllMarvelsChars(offset, limit)


        if (response != null) {
            response.body()!!.data.results.forEach() {
                val m = it.getMarvelChars()
                itemList.add(m)
            }
        }
        return itemList
    }
}