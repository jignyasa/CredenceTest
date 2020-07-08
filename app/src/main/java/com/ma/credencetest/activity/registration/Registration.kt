package com.ma.credencetest.activity.registration

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.ma.credencetest.R
import com.ma.credencetest.activity.login.Login
import com.ma.credencetest.helper.DateUtility
import com.ma.credencetest.helper.NetworkHelper
import com.ma.credencetest.helper.SessionHelper
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_registration.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.util.*
import java.util.regex.Pattern


class Registration : AppCompatActivity() ,View.OnClickListener{
    private var cal=Calendar.getInstance()
    private lateinit var helper:SessionHelper
    private lateinit var datePickerDialog:DatePickerDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        helper=SessionHelper(this)
        initDatePicker()
        edtBirthdate.setOnClickListener (this)
        btnRegistration.setOnClickListener (this)
        tvLogin.setOnClickListener(this)
    }

    private fun initDatePicker() {
        datePickerDialog =
            DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    val calendar=Calendar.getInstance()
                    calendar.set(year,month,dayOfMonth)
                    edtBirthdate.setText(DateUtility.getFormateDate(calendar,"dd-MM-yyyy"))
                }
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE))
    }

    private fun registerUser() {
        if(NetworkHelper.isInternetAvailable(this))
        {
            com.ma.credencetest.remote.Retrofit.getRetrofit().doRegistration(
                edtEmail.text.toString(),
                edtPassword.text.toString(),
                edtFirstName.text.toString(),
                edtLastName.text.toString(),
                edtBirthdate.text.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object:Observer<JsonObject>{
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: JsonObject) {
                        Log.e("data",t.toString())
                        val success=t.get("success").asInt
                        if(success==1) {
                            helper.userId="1"
                            makeToast(this@Registration,"Register Successfully.")
                            finish()
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

    private fun validation():Boolean {
        val isValid: Boolean
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        if (edtFirstName.text.toString().isEmpty()) {
            isValid=false
            makeToast(this, "please enter proper first name")
        } else if (edtLastName.text.toString().isEmpty()) {
             isValid=false
            makeToast(this, "please enter proper last name")
        } else if (edtEmail.text.toString().isEmpty() || !pattern.matcher(edtEmail.text?.trim().toString()).matches()) {
             isValid=false
            makeToast(this, "please enter proper email")
        } else if (edtBirthdate.text.toString().isEmpty()) {
             isValid=false
            makeToast(this, "please select proper birthdate")
        } else if (edtPassword.text.toString().isEmpty()) {
             isValid=false
            makeToast(this, "please enter proper password")
        } else if (edtConfirmPassword.text.toString().isEmpty()) {
             isValid=false
            makeToast(this, "please enter proper confirm password")
        }
        else
            isValid = true

        return isValid
    }

    private fun makeToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.btnRegistration->{
                if(validation())
                    registerUser()
            }
            R.id.edtBirthdate->datePickerDialog.show()
            R.id.tvLogin->
            {
                startActivity(Intent(this,Login::class.java))
                finish()
            }
        }
    }
}