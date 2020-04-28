package com.ilham.made.secondsubmission.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object Helper {
    fun changeDateFormat(date: String?, time: String?): String? = if (!date.isNullOrEmpty()) {
        if (time.isNullOrEmpty()) {
            val dateResult: Date? = dateFormatter("yyyy-MM-dd").parse(date)
            val dateFormat = SimpleDateFormat("dd MMMM yyy", Locale.getDefault())
            dateFormat.format(dateResult!!)
        } else {
            val dateResult: Date? = dateFormatter("yyyy-MM-dd hh:mm").parse("$date $time")
            val dateTimeFormat = SimpleDateFormat("dd MMMM yyyy hh:mm aa", Locale.getDefault())
            dateTimeFormat.timeZone = TimeZone.getTimeZone("GMT+7")
            dateTimeFormat.format(dateResult!!)
        }
    } else {
        "-"
    }

    private fun dateFormatter(format: String): DateFormat = SimpleDateFormat(format, Locale.getDefault())

    fun replaceStringForFootballData(string: String?): String? = string?.replace(";", "\n")

    fun replaceStringForPlayerData(string: String?): String? = string?.replace("; ", "\n")

    fun setImage(context: Context, imagePath: String?, view: ImageView) {
        Glide.with(context)
            .load(imagePath)
            .into(view)
    }
}







