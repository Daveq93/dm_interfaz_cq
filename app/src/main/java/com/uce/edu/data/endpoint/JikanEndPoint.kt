package com.uce.edu.data.endpoint

import com.uce.edu.data.entity.jkan.JikanAnimeEntiy
import retrofit2.Response
import retrofit2.http.GET

interface JikanEndPoint {

    @GET("top/anime")
    suspend  fun getAllAnimes(): Response<JikanAnimeEntiy>
}