package com.example.youtubeproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView

class YoutubeItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube_item)

        val videoUrl = intent.getStringExtra("video_url")
        // 비디오 재생을 위한 컨트롤러
        val mediaController = MediaController(this)
        findViewById<VideoView>(R.id.youtube_video_view).apply {
            this.setVideoPath(videoUrl)
            this.requestFocus()
            this.start()
            mediaController.show()
        }
    }

    // ExoPlayer
    // 영상을 본격적으로 넣어야겠다고 한다면 ExoPlayer 를 사용하는게 좋다.
    // - 기능이 다양하다
    // - 사용이 쉽다
    // - DRM
}