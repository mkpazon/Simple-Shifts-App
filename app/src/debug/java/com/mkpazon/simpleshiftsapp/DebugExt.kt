package com.mkpazon.simpleshiftsapp

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient

fun OkHttpClient.Builder.configEnvt(): OkHttpClient.Builder {
    this.addInterceptor(StethoInterceptor())
    return this
}