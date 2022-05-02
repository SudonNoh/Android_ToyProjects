package com.example.youtubeproject

import retrofit2.Call
import retrofit2.http.GET
import java.io.Serializable

class YoutubeItem(
    val id: Int, val title: String, val content: String, val video: String, val thumbnail: String
)

class MelonItem(
    val id: Int, val title: String, val song: String, val thumbnail: String
) : Serializable

interface RetrofitService {

    @GET("youtube/list/")
    fun getYoutubeItemList(): Call<ArrayList<YoutubeItem>>

    @GET("melon/list/")
    fun getMelonItemList(): Call<ArrayList<MelonItem>>
}