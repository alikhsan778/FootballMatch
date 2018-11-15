package com.example.alikhsan.footballmatchschedule


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.alikhsan.footballmatchschedule.R.id.it_fav
import com.example.alikhsan.footballmatchschedule.R.id.it_teams
import com.example.alikhsan.footballmatchschedule.fragment.FragmentFavorite
import com.example.alikhsan.footballmatchschedule.fragment.FragmentMain

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var nMenu: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        bt_nav_team_or_fav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                it_teams -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fm_container, FragmentMain(), FragmentMain::class.java.simpleName)
                        .commit()
                }

                it_fav -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fm_container, FragmentFavorite(), FragmentFavorite::class.java.simpleName)
                        .commit()
                }
            }
            true
        }
        bt_nav_team_or_fav.selectedItemId = it_teams
    }
}
