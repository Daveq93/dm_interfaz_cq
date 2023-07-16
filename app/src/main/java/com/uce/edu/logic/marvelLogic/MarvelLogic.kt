package com.uce.edu.logic.marvelLogic

import android.util.Log
import com.uce.edu.data.connections.ApiConnection
import com.uce.edu.data.endpoint.MarvelEndPoint
import com.uce.edu.data.entity.marvel.characters.database.MarvelCharsDB
import com.uce.edu.data.entity.marvel.characters.database.getMarvelChars
import com.uce.edu.data.entity.marvel.characters.getMarvelChars
import com.uce.edu.logic.data.MarvelChars
import com.uce.edu.logic.data.getMarvelCharsDB
import com.uce.edu.ui.utilities.DispositivosMoviles
import kotlin.math.log

class MarvelLogic {


    suspend fun getMarvelChars(name: String, limit: Int): List<MarvelChars> {

        var itemList = arrayListOf<MarvelChars>()
        var response = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndPoint::class.java
        ).getCharactersStarWith(name, limit)

        if(response.isSuccessful){
            response.body()!!.data.results.forEach{
                val m=it.getMarvelChars()
                itemList.add(m)
            }
        }else{

        }
        return itemList
    }


    suspend fun getAllMarvelChars(offset: Int, limit: Int): ArrayList<MarvelChars> {
        var itemList = arrayListOf<MarvelChars>()
        val response = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndPoint::class.java
        ).getAllMarvelsChars(offset, limit)

        if (response.isSuccessful) {
            response.body()!!.data.results.forEach{
                val m = it.getMarvelChars()
                itemList.add(m)
            }
        }else{
            Log.d("DM",response.toString())
        }
        return itemList
    }

    suspend fun getAllMarvelCharDB(): List<MarvelChars> {
        var items: ArrayList<MarvelChars> = ArrayList()
        val itemsAux = DispositivosMoviles.getDBInstance().marvelDao().getAllCharacters()
        itemsAux.forEach {
            items.add(
                it.getMarvelChars()
            )
        }
        return items
    }


    suspend fun insertMarvelCharstoDB(items: List<MarvelChars>) {
        var itemsDB = arrayListOf<MarvelCharsDB>()
        items.forEach {
            itemsDB.add(it.getMarvelCharsDB())
        }

        DispositivosMoviles.getDBInstance().marvelDao().insertMarvelCharacter(itemsDB)

    }

    suspend fun getInitChars(limit: Int,offset: Int): MutableList<MarvelChars> {
        var items = mutableListOf<MarvelChars>()
        try{
            items = MarvelLogic()
                .getAllMarvelCharDB()
                .toMutableList()
            if (items.isEmpty()){
                items=(MarvelLogic().getAllMarvelChars(
                    offset = offset, limit = limit
                ))
                MarvelLogic().insertMarvelCharstoDB(items)
            }
            return items
        }catch (ex:Exception){
            throw RuntimeException(ex.message)
        }
    }
}