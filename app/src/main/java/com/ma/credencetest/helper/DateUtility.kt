package com.ma.credencetest.helper

import java.text.SimpleDateFormat
import java.util.*

class DateUtility {
    companion object{
        fun getFormateDate(cal:Calendar,formate:String):String
        {
            val sdf=SimpleDateFormat(formate, Locale.ENGLISH)
            return sdf.format(cal.time)
        }
    }
}