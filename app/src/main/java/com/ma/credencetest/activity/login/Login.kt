package com.ma.credencetest.activity.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.gson.JsonObject
import com.ma.credencetest.R
import com.ma.credencetest.SessionHelper
import com.ma.credencetest.activity.registration.Registration
import com.ma.credencetest.helper.NetworkHelper
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registration.*
import java.util.regex.Pattern

class Login : AppCompatActivity(),View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        registerNow.setOnClickListener(this)
        login.setOnClickListener(this)
    }
    private fun validation():Boolean {
        val isValid: Boolean
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
         if (edtEmail.text.toString().isEmpty() || !pattern.matcher(edtEmail.text.toString()).matches()) {
            isValid=false
            makeToast(this, "please enter proper email")
        } else if (edtPassword.text.toString().isEmpty()) {
             isValid = false
             makeToast(this, "please enter proper password")
         }
        else
            isValid = true

        return isValid
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.login->{
                if(validation())
                    doLogin()

            }
            R.id.registerNow->
            {
                startActivity(Intent(this,Registration::class.java))
            }
        }
    }

    private fun doLogin() {
        if(NetworkHelper.isInternetAvailable(this))
        {
            com.ma.credencetest.remote.Retrofit.getRetrofit().doLogin(
                edtEmail.text.toString(),
                edtPassword.text.toString()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<JsonObject> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: JsonObject) {
                        val success=t.get("success") as Int
                        if(success==1) {
                            val helper = SessionHelper(this@Login)
                            helper.isLogin = true
                        }
                    }

                    override fun onError(e: Throwable) {
                        Log.e("error",e.message)
                    }

                })
        }
        else
            NetworkHelper.openSettingDialog(this)
    }

    private fun makeToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}