package com.ma.credencetest.helper

import android.content.Context
import android.content.SharedPreferences
import com.ma.credencetest.BuildConfig

class SessionHelper(private val mContext: Context) {
    private val SHARED_PREFERENCE =
        BuildConfig.APPLICATION_ID
    private val preferences: SharedPreferences
    val editor: SharedPreferences.Editor

    companion object {
        const val IS_LOGIN = "isLogin"
        const val USER_ID = "userId"
    }

    init {
        preferences = mContext.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)
        editor = preferences.edit()
        editor.apply()
    }

    var isLogin: Boolean
        get() = preferences.getBoolean(IS_LOGIN, false)
        set(value) {
            editor.putBoolean(IS_LOGIN, value).apply()
        }
    var userId: String
        get() = preferences.getString(USER_ID,"0").toString()
        set(value) {
            editor.putString(USER_ID, value).apply()
        }
}