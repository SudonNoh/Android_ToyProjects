package com.example.youtubeproject

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class InstaChangeProfileActivity : AppCompatActivity() {

    var imageUri: Uri? = null
    var glide: RequestManager? = null
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insta_change_profile)

        imageView = findViewById(R.id.profile_img)

        glide = Glide.with(this)

//      uri : 파일, 웹사이트 등이 저장된 위치
        val imagePickerLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                imageUri = it.data!!.data
//                이미지의 주소
//                Log.d("instaa", ""+imageUri)
                glide!!.load(imageUri).into(imageView)
            }
        imagePickerLauncher.launch(
            Intent(Intent.ACTION_PICK).apply {
                this.type = MediaStore.Images.Media.CONTENT_TYPE
            }
        )

        findViewById<TextView>(R.id.complete_btn).setOnClickListener {
//          파일의 Uri를 받아 파일을 가져오는 과정
            val file = getRealFile(imageUri!!)
            val requestFile = RequestBody.create(
                MediaType.parse(
                    this.contentResolver.getType(imageUri!!)
                ), file
            )
            val body = MultipartBody.Part.createFormData("image", file!!.name, requestFile)
            val header = HashMap<String, String>()
            // 일전에 token 을 sharedPreference 에 저장해두었음.
            val sp = this.getSharedPreferences(
                "user_info",
                Context.MODE_PRIVATE
            )
            val token = sp.getString("token", "")
            header["Authorization"] = "token " + token!!
            val userId: Int = sp.getString("user_id", "")!!.toInt()

            val retrofit = Retrofit.Builder()
                .baseUrl("http://mellowcode.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val retrofitService = retrofit.create(RetrofitService::class.java)
            val user = RequestBody.create(MultipartBody.FORM, userId.toString())
            retrofitService.changeProfile(userId, header, body, user)
                .enqueue(object : Callback<Any> {
                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                this@InstaChangeProfileActivity,
                                "변경완료",
                                Toast.LENGTH_SHORT
                            ).show()
//                          뒤로가기
                            onBackPressed()
//                          단순히 뒤로가기 한 경우 A -> B 로 사진을 변경해야 하는데,
//                          변경되지 않음. 그 작업은 instaProfileFragment 에서 진행해야 함.
                        }
                    }

                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        Toast.makeText(
                            this@InstaChangeProfileActivity,
                            "변경 실패",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }

    private fun getRealFile(uri: Uri): File? {
        var uri: Uri? = uri
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        if (uri == null) {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        var cursor: Cursor? = this.getContentResolver().query(
            uri!!,
            projection,
            null,
            null,
            MediaStore.Images.Media.DATE_MODIFIED + " desc"
        )
        if (cursor == null || cursor.getColumnCount() < 1) {
            return null
        }
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val path: String = cursor.getString(column_index)
        if (cursor != null) {
            cursor.close()
            cursor = null
        }
        return File(path)
    }

}