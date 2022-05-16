package com.example.youtubeproject

import android.accessibilityservice.GestureDescription
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    val id: Int, val content: String, val image: String, val owner_profile: OwnerProfile
)

class OwnerProfile(
    val username: String, val image: String?
)

class UserInfo(
    val id: Int,
    val username: String,
    val profile: OwnerProfile
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

    @POST("instagram/post/like/{post_id}/")
    fun postLike(
        // Path 의 인자로 들어가는 post_id : url 에 입력되는 'post_id'
        // 그 옆에 post_id : 받아온 'post_id'
        @Path("post_id") post_id: Int
    ): Call<Any>

    // Multipart : 파일을 보낼 때 사용
    @Multipart
    @POST("instagram/post/")
    fun uploadPost(
        // post 한 사람을 확인하기 위해 Token 값을 활용해야 함.
        // Token 값을 보낼 때에는 Header 를 사용
        @HeaderMap headers: Map<String, String>,
        // Multipart 는 파일을 조각내서 보내는 개념
        // image 를 조각내서 전송
        @Part image: MultipartBody.Part,
        @Part("content") content: RequestBody
    ): Call<Any>

    @GET("user/userInfo/")
    fun getUserInfo(
        @HeaderMap headers: Map<String, String>,
    ): Call<UserInfo>

    @Multipart
    @PUT("user/profile/{user_id}/")
    fun changeProfile(
        @Path("user_id") userId : Int,
        @HeaderMap headers: Map<String, String>,
        @Part image : MultipartBody.Part,
        @Part("user") user: RequestBody,
    ): Call<Any>
}