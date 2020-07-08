package com.ma.credencetest.remote

import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.*
import java.util.*
import kotlin.collections.ArrayList

interface ApiService {
    @POST("login.php")
    @FormUrlEncoded
    fun doLogin(@Field("user_email") userName:String,
                 @Field("user_password") password:String) : Observable<JsonObject>

    @POST("userList.php")
    @FormUrlEncoded
    fun getUserData(@Field("user_id") userId:String) : Observable<JsonObject>

    @POST("registrationWithProfilePictureWith_File.php")
    @FormUrlEncoded
    fun doRegistration(@Field("user_email") userEmail:String,
                       @Field("user_password") userPassword:String,
                       @Field("user_firstname") userFirstName:String,
                       @Field("user_lastname") userLastName:String,
                       @Field("user_birthdate") userBirthDate:String,
                       @Field("user_profile_picture_data") userPhoto:String="jigu") : Observable<JsonObject>

}