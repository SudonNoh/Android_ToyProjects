package com.example.youtubeproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class InstaFeedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.insta_feed_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // findViewById method 는 activity 에서 사용할 수 있는 함수, Fragment 에서는 사용 x
        // onViewCreated 는 activity 가 완성된 후에 실행되는 함수, activity 요소는 view에 포함되어져 전달
        // 따라서 item 은 view 에서 찾아서 사용할 수 있음
        val feedListView = view.findViewById<RecyclerView>(R.id.feed_list)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(RetrofitService::class.java)

        retrofitService.getInstagramPosts().enqueue(object : Callback<ArrayList<InstaPost>> {
            override fun onResponse(
                call: Call<ArrayList<InstaPost>>,
                response: Response<ArrayList<InstaPost>>
            ) {
                val postList = response.body()
                val postRecyclerView = view.findViewById<RecyclerView>(R.id.feed_list)
                postRecyclerView.adapter = PostRecyclerViewAdapter(
                    postList!!,
                    // LayoutInflater.from 안에 context 로 activity 를 넣어주어야 하는데
                    // fragment 에서는 activity 를 따로 찾아서 넣어주어야 한다. activity 에서는 this 로
                    // 입력한다. 이 fragment 는 결국 instaMainActivity 위에 그려지기 때문에 context 로
                    // 받아야 하는 activity 는 instaMainActivity 이다.
                    LayoutInflater.from(activity),
                    Glide.with(activity!!)
                )
            }

            override fun onFailure(call: Call<ArrayList<InstaPost>>, t: Throwable) {
            }
        })
    }

    class PostRecyclerViewAdapter(
        val postList: ArrayList<InstaPost>,
        val inflater: LayoutInflater,
        val glide: RequestManager
    ) : RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val ownerImg: ImageView
            val ownerUsername: TextView
            val postImg: ImageView
            val postContent: TextView

            init {
                ownerImg = itemView.findViewById(R.id.owner_img)
                ownerUsername = itemView.findViewById(R.id.owner_username)
                postImg = itemView.findViewById(R.id.post_img)
                postContent = itemView.findViewById(R.id.post_content)
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): PostRecyclerViewAdapter.ViewHolder {
            return ViewHolder(
                inflater.inflate(R.layout.insta_post_item, parent, false)
            )
        }

        override fun onBindViewHolder(holder: PostRecyclerViewAdapter.ViewHolder, position: Int) {
            val post = postList[position]

            // image?.let 을 하면 image 가 null 이 아닌 경우에만 실행
            // RetrofitService 에서 image : String 에 ? 를 붙여서 nullable 로 변경
            // circleCrop 으로 동그랗게 사진을 조정해준다.
            post.owner_profile.image?.let {
                glide.load(it).centerCrop().circleCrop().into(holder.ownerImg)
            }
            post.image.let {
                glide.load(it).centerCrop().into(holder.postImg)
            }
            holder.ownerUsername.text = post.owner_profile.username
            holder.postContent.text = post.content
        }

        override fun getItemCount(): Int {
            return postList.size
        }
    }
}

