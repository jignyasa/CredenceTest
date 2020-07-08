package com.ma.credencetest

import android.content.Context
import android.content.SharedPreferences

class SessionHelper(private val mContext: Context) {
    private val SHARED_PREFERENCE = BuildConfig.APPLICATION_ID
    private val preferences: SharedPreferences
    val editor: SharedPreferences.Editor

    companion object {
        const val IS_LOGIN = "isLogin"
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
}