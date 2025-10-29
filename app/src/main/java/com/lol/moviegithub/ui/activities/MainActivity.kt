package com.lol.moviegithub.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.lol.moviegithub.R
import com.lol.moviegithub.databinding.ActivityMainBinding
import com.lol.movies.ui.fragments.NowPlayingFragment
import com.lol.movies.ui.fragments.PopularFragment
import com.lol.movies.ui.fragments.SearchFragment
import com.lol.movies.ui.fragments.TopRatedFragment
import com.lol.movies.ui.fragments.UpComingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val nowPlayingFragment = NowPlayingFragment()
    val popularFragment = PopularFragment()
    val topRatedFragment = TopRatedFragment()
    val upComingFragment = UpComingFragment()
    val searchFragment = SearchFragment()
    lateinit var active: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        active = nowPlayingFragment

        setCurranFragment(upComingFragment, 5)
        setCurranFragment(topRatedFragment, 4)
        setCurranFragment(searchFragment, 3)
        setCurranFragment(popularFragment, 2)
        setCurranFragment(nowPlayingFragment, 1)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nowPlaying -> showCurranFragment(nowPlayingFragment)
                R.id.popular -> showCurranFragment(popularFragment)
                R.id.search -> showCurranFragment(searchFragment)
                R.id.topRated -> showCurranFragment(topRatedFragment)
                R.id.upComing -> showCurranFragment(upComingFragment)
            }
            true
        }
    }

    private fun setCurranFragment(fragment: Fragment, num: Int) {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.main_frame_layout, fragment, num.toString())
            if (num != 1) {
                hide(fragment).commit()
            } else {
                commit()
            }
        }
    }

    private fun showCurranFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            hide(active)
            show(fragment)
            commit()
            active = fragment
        }
    }
}