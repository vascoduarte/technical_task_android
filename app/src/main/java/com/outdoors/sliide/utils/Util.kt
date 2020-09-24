package com.outdoors.sliide.utils

import android.util.Patterns
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun validateField(field: String):Boolean
{
    return field.isNotEmpty()
}
fun validateEmail(email:String):Boolean
{
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(email).matches()

}
fun getFormattedCurrentTime():String
{
    val df: DateFormat = SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss")
   return df.format(Calendar.getInstance().time)
}
