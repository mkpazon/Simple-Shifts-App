package com.mkpazon.simpleshiftsapp.di.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    companion object {
        const val AUTH_KEY = "Authorization"
        const val AUTH_VAL = "Deputy f1b5a91d4d6ad523f2610114591c007e75d15084"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        requestBuilder.addHeader(AUTH_KEY, AUTH_VAL)

        return chain.proceed(requestBuilder.build())
    }
}