package com.mkpazon.simpleshiftsapp.ui.util

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    activity?.let { Toast.makeText(it, message, length).show() }
}