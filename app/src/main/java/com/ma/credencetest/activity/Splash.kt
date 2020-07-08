package com.ma.credencetest.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ma.credencetest.R
import com.ma.credencetest.helper.SessionHelper
import com.ma.credencetest.activity.login.Login
import com.ma.credencetest.activity.registration.Registration

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val helper= SessionHelper(this)
        Handler().postDelayed({
            if(helper.isLogin)
                startActivity(Intent(this,UserList::class.java))
            else
                startActivity(Intent(this,Login::class.java))
        },3000)
    }
}