package com.example.youtubeproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InstaProfileFragment : Fragment() {

    lateinit var userProfileImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.insta_profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userProfileImageView = view.findViewById(R.id.profile_img)

        view.findViewById<TextView>(R.id.edit_btn).setOnClickListener {
            startActivity(
                Intent(
                    activity as instaMainActivity,
                    instaChangeProfileActivity::class.java
                )
            )
        }
    }

//  lifecycle 에 따라서 이미지를 선택 후 완료 버튼을 누른 상태에서 다시 foreground 로 가게 되면
//  onResume 부분이 실행된다.
    override fun onResume() {
        super.onResume()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(RetrofitService::class.java)

        val header = HashMap<String, String>()
        // 일전에 token 을 sharedPreference 에 저장해두었음.
        val sp = (activity as instaMainActivity).getSharedPreferences(
            "user_info",
            Context.MODE_PRIVATE
        )
        val token = sp.getString("token", "")
        header["Authorization"] = "token " + token!!

        val glide = Glide.with(activity as instaMainActivity)

        retrofitService.getUserInfo(header).enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.isSuccessful) {
                    val userInfo: UserInfo = response.body()!!
                    userInfo.profile.image?.let {
                        glide.load(it).into(userProfileImageView)
                    }
                    if (userInfo.profile.image != null) {
                    }
                }
            }
            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
            }
        })
    }
}