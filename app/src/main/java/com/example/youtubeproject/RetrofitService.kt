package com.example.youtubeproject

import retrofit2.Call
import retrofit2.http.*
import java.io.Serializable

class YoutubeItem(
    val id: Int, val title: String, val content: String, val video: String, val thumbnail: String
)

class MelonItem(
    val id: Int, val title: String, val song: String, val thumbnail: String
) : Serializable

class User(
    val username: String, val token: String, val id: Int
)

class InstaPost(
    val content: String, val image: String, val owner_profile: OwnerProfile
)

class OwnerProfile(
    val username: String, val image: String?
)

interface RetrofitService {

    @GET("youtube/list/")
    fun getYoutubeItemList(): Call<ArrayList<YoutubeItem>>

    @GET("melon/list/")
    fun getMelonItemList(): Call<ArrayList<MelonItem>>

    // User 객체를 만들지 않고 hashmap type 으로 처리
    // 사용빈도가 적을 거 같아서
    // FormUrlEncoded, @FieldMap : Postman 에서 Form 형태로 보낼 때 사용
    // json 형태로 보낼 때는 사용 X
    @POST("user/login/")
    @FormUrlEncoded
    fun instaLogin(
        @FieldMap params: HashMap<String, Any>
    ): Call<User>

    @POST("user/signup/")
    @FormUrlEncoded
    fun instaJoin(
        @FieldMap params: HashMap<String, Any>
    ): Call<User>

    @GET("instagram/post/list/all/")
    fun getInstagramPosts(
    ): Call<ArrayList<InstaPost>>
}