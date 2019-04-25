package com.mkpazon.simpleshiftsapp.util

import com.mkpazon.simpleshiftsapp.DEFAULT_DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(format: String = DEFAULT_DATE_FORMAT): Date {
    val dateFormat = SimpleDateFormat(format, Locale.US)
    return dateFormat.parse(this)
}