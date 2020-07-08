package com.ma.credencetest.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.ma.credencetest.R
import com.ma.credencetest.adapter.UserAdapter
import com.ma.credencetest.helper.NetworkHelper
import com.ma.credencetest.helper.SessionHelper
import com.ma.credencetest.model.ListItem
import com.ma.credencetest.remote.Retrofit
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user_list.*

class UserList : AppCompatActivity() {
    private lateinit var adapter:UserAdapter
    private lateinit var helper:SessionHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        helper=SessionHelper(this)
        init()
    }

    private fun init() {
        adapter= UserAdapter(this)
        rvList.adapter=adapter
        rvList.layoutManager=LinearLayoutManager(this)
        getUserData()
    }

    private fun getUserData() {
        if(NetworkHelper.isInternetAvailable(this))
        {
            Retrofit.getRetrofit().getUserData(helper.userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<JsonObject> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: JsonObject) {
                        val success=t.get("success").asInt
                        if(success==1) {
                           // helper.isLogin = true
                            var list=ArrayList<ListItem>()
                            val array=t.getAsJsonArray("userlist")
                            for(i in 0 until array.size())
                            {
                                val jsonObject=array[i] as JsonObject
                                list.add(ListItem(jsonObject.get("user_id").asString,
                                    jsonObject.get("user_email").asString,
                                    jsonObject.get("user_password").asString,
                                    jsonObject.get("user_firstname").asString,
                                    jsonObject.get("user_lastname").asString,
                                    jsonObject.get("user_birthdate").asString,
                                    jsonObject.get("user_profile_picture_url").asString))
                            }
                            adapter.addData(list)
                            Log.e("data list",t.toString())
                        }
                    }

                    override fun onError(e: Throwable) {
                        Log.e("error",e.message)
                    }

                })
        }
    }
}