package com.mkpazon.simpleshiftsapp.util

fun Boolean?.orFalse() = this ?: false

fun Boolean?.orTrue() = this ?: true