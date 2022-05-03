package com.example.youtubeproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// token 소유 여부를 확인하기 위한 activity 만약 token 이 있으면 Detail 창
// 없으면 login 창으로 이동
class instaSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insta_splash)

        val sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "empty")
        when(token){
            "empty"->{
                // 로그인이 되어있지 않은 경우
                startActivity(Intent(this, instaLoginActivity::class.java))
            }
            else -> {
                // 로그인이 되어있는 경우
                startActivity(Intent(this, instaMainActivity::class.java))
            }
        }
    }
}