package com.ma.credencetest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.close
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.ma.credencetest.activity.UserList
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerToggle = ActionBarDrawerToggle(this, drawerPanel, R.string.open, R.string.close)
        drawerPanel.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navDrawer.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard -> startActivity(Intent(this, UserList::class.java))
                else -> return@OnNavigationItemSelectedListener true
            }
            drawerPanel.closeDrawer(GravityCompat.START)
            true
        })

    }
}