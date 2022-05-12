package com.example.youtubeproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class InstaFeedFragment : Fragment() {
    // 전역변수로 사용하기 위해 따로 빼놓음(좋아요 기능을 만들기 위함)
    lateinit var retrofitService: RetrofitService
    lateinit var postRecyclerView: RecyclerView
    lateinit var postList: ArrayList<InstaPost>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postList = arrayListOf()
        super.onCreate(savedInstanceState)
        var postInflater = inflater.inflate(R.layout.insta_feed_fragment, container, false)
        postRecyclerView = postInflater.findViewById<RecyclerView>(R.id.feed_list)
        postRecyclerView.adapter = PostRecyclerViewAdapter(
            postList!!,
            // LayoutInflater.from 안에 context 로 activity 를 넣어주어야 하는데
            // fragment 에서는 activity 를 따로 찾아서 넣어주어야 한다. activity 에서는 this 로
            // 입력한다. 이 fragment 는 결국 instaMainActivity 위에 그려지기 때문에 context 로
            // 받아야 하는 activity 는 instaMainActivity 이다.
            LayoutInflater.from(activity),
            Glide.with(this@InstaFeedFragment),
            // instaFeedFragment 를 받는 이유는 위에서 만든 postLike 를 Adapter 에서 사용하기 위해
            this@InstaFeedFragment,
            activity as (instaMainActivity)
        )
        return postInflater
//        return inflater.inflate(R.layout.insta_feed_fragment, container, false)
    }

    fun postLike(post_id: Int) {
        retrofitService.postLike(post_id).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Toast.makeText(activity, "좋아요!", Toast.LENGTH_SHORT).show()
                // 여기서는 view 를 불러오기 어렵기 때문에 Thread 에서 visibility 설정을 바꿔주기
                // 어렵다. 따라서 이 부분을 Adapter 에서 진행하도록 한다.
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Toast.makeText(activity, "좋아요 실패!", Toast.LENGTH_SHORT).show()
            }
        })
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
        retrofitService = retrofit.create(RetrofitService::class.java)

        retrofitService.getInstagramPosts().enqueue(object : Callback<ArrayList<InstaPost>> {
            override fun onResponse(
                call: Call<ArrayList<InstaPost>>,
                response: Response<ArrayList<InstaPost>>
            ) {
                postList = response.body()!!
                postRecyclerView = view.findViewById<RecyclerView>(R.id.feed_list)
                postRecyclerView.adapter = PostRecyclerViewAdapter(
                    postList!!,
                    // LayoutInflater.from 안에 context 로 activity 를 넣어주어야 하는데
                    // fragment 에서는 activity 를 따로 찾아서 넣어주어야 한다. activity 에서는 this 로
                    // 입력한다. 이 fragment 는 결국 instaMainActivity 위에 그려지기 때문에 context 로
                    // 받아야 하는 activity 는 instaMainActivity 이다.
                    LayoutInflater.from(activity),
                    Glide.with(activity!!),
                    // instaFeedFragment 를 받는 이유는 위에서 만든 postLike 를 Adapter 에서 사용하기 위해
                    this@InstaFeedFragment,
                    activity as (instaMainActivity)
                )
            }

            override fun onFailure(call: Call<ArrayList<InstaPost>>, t: Throwable) {
            }
        })
    }

    class PostRecyclerViewAdapter(
        val postList: ArrayList<InstaPost>,
        val inflater: LayoutInflater,
        val glide: RequestManager,
        // instaFeedFragment 를 받는 이유는 위에서 만든 postLike 를 Adapter 에서 사용하기 위함이다.
        val instaFeedFragment: InstaFeedFragment,
        // Thread 에서 View 를 사용하기 위해서 activity 를 context 로 받는다.
        val activity: instaMainActivity
    ) : RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val ownerImg: ImageView
            val ownerUsername: TextView
            val postImg: ImageView
            val postContent: TextView
            val postLayer : ImageView
            val postHeart : ImageView

            init {
                ownerImg = itemView.findViewById(R.id.owner_img)
                ownerUsername = itemView.findViewById(R.id.owner_username)
                postImg = itemView.findViewById(R.id.post_img)
                postContent = itemView.findViewById(R.id.post_content)
                postLayer = itemView.findViewById(R.id.post_layer)
                postHeart = itemView.findViewById(R.id.post_heart)

                postImg.setOnClickListener {
                    instaFeedFragment.postLike(postList[adapterPosition].id)
                    Thread {
                        activity.runOnUiThread {
                            postLayer.visibility = VISIBLE
                            postHeart.visibility = VISIBLE
                        }
                        Thread.sleep(2000)
                        activity.runOnUiThread {
                            postLayer.visibility = INVISIBLE
                            postHeart.visibility = INVISIBLE
                        }
                    }.start()
                }
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

