package com.ma.credencetest.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiResponse<T> {
    @Expose
    @SerializedName("success")
    val result = 0

    @SerializedName("message")
    @Expose
    val message: String = ""

    @Expose
    @SerializedName("data")
    var data: T? = null
}