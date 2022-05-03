package com.example.youtubeproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class instaJoinActivity : AppCompatActivity() {

    var username: String = ""
    var password1: String = ""
    var password2: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insta_join)

        findViewById<TextView>(R.id.login_btn).setOnClickListener {
            startActivity(Intent(this, instaLoginActivity::class.java))
        }
        findViewById<EditText>(R.id.id_input).doAfterTextChanged { username = it.toString() }
        findViewById<EditText>(R.id.id_input).doAfterTextChanged { password1 = it.toString() }
        findViewById<EditText>(R.id.id_input).doAfterTextChanged { password2 = it.toString() }

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(RetrofitService::class.java)


        findViewById<TextView>(R.id.join_btn).setOnClickListener {

            val user = HashMap<String, Any>()
            user["username"] = username
            user["password1"] = password1
            user["password2"] = password2

            retrofitService.instaJoin(user).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val user: User = response.body()!!
                        val sharedPreferences =
                            getSharedPreferences("user_info", Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("token", user.token)
                        editor.putString("user_id", user.id.toString())
                        editor.apply()
                        startActivity(Intent(this@instaJoinActivity, instaMainActivity::class.java))
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                }
            })
        }
    }
}