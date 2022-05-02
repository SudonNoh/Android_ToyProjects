package com.example.youtubeproject

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView

class MelonDetailActivity : AppCompatActivity() {

    lateinit var playPauseButton: ImageView
    lateinit var mediaPlayer: MediaPlayer
    var position = 0

    // 스위치를 도입한다: 지금 재생 중인지 아닌지 확인(재생중이면 PAUSE 버튼)
    var is_playing: Boolean = true
        set(value) {
            if (value) {
                playPauseButton.setImageDrawable(
                    this.resources.getDrawable(R.drawable.pause, this.theme)
                )
            } else {
                playPauseButton.setImageDrawable(
                    this.resources.getDrawable(R.drawable.play, this.theme)
                )
            }
            field = value
        }
    lateinit var melonItemList: ArrayList<MelonItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_melon_detail)

        // serializable로 받고, ArrayList 타입으로 변경한다.
        var melonItemList = intent.getSerializableExtra("melon_item_list") as ArrayList<MelonItem>
        position = intent.getIntExtra("position", 0)
        playMelonItem(melonItemList[position])

        playPauseButton = findViewById(R.id.play)
        playPauseButton.setOnClickListener {
            if (is_playing) {
                is_playing = false
                mediaPlayer.stop()
            } else {
                is_playing = true
                playMelonItem(melonItemList[position])
            }
        }
    }

    fun playMelonItem(melonItem: MelonItem) {
        mediaPlayer = MediaPlayer.create(
            this,
            Uri.parse(melonItem.song)
        )
        mediaPlayer.start()
    }
}