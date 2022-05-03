package com.example.youtubeproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class instaMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insta_main)

        val tabs = findViewById<TabLayout>(R.id.main_tab)
        val tabIconArray = arrayOf(
            R.drawable.btn_outsta_home,
            R.drawable.btn_outsta_post,
            R.drawable.btn_outsta_my
        )
        val pager = findViewById<ViewPager2>(R.id.main_pager)
        pager.adapter = InstaMainPageAdapter(this, 3)

        TabLayoutMediator(tabs, pager) { tab, position ->
            tabs.addTab(tabs.newTab().setIcon(tabIconArray[position]))
        }.attach()

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                pager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
}

class InstaMainPageAdapter(
    fragmentActivity: FragmentActivity,
    val tabCount: Int
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return tabCount
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return InstaFeedFragment()
            1 -> return InstaPostFragment()
            else -> return InstaProfileFragment()
        }
    }
}