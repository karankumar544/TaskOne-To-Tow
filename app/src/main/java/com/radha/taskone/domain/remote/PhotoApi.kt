package com.radha.taskone.domain.remote

import com.radha.taskone.domain.remote.dto.Photos
import com.radha.taskone.util.Constant.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoApi {

    @GET("services/rest")
    suspend fun getPhotos(
        @Query("method") methode: String = "flickr.photos.search",
        @Query("text") text: String ,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("extras") extras: String = "url_s",
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: Int = 1,
    ): Response<Photos>

}