package com.uce.edu.logic.jikanLogic

import com.uce.edu.data.connections.ApiConnection
import com.uce.edu.data.endpoint.JikanEndPoint
import com.uce.edu.data.marvel.MarvelChars

class JikanAnimeLogic {

  suspend fun getAllAnimes():List<MarvelChars>{
        var call = ApiConnection.getJikanConnection()
       val response = call!!.create(JikanEndPoint::class.java)?.getAllAnimes()

        var itemList = arrayListOf<MarvelChars>()

      if (response != null) {
          if(response.isSuccessful){
              if (response != null) {
                  response.body()!!.data.forEach{
                      val m = MarvelChars(it.mal_id,
                          it.title,
                          it.titles[0].title,
                          it.images.jpg.image_url)
                      itemList.add(m)

                  }
              }
          }
      }
        return itemList
    }
}