package com.mkpazon.simpleshiftsapp.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mkpazon.simpleshiftsapp.BASE_URL
import com.mkpazon.simpleshiftsapp.BuildConfig
import com.mkpazon.simpleshiftsapp.configEnvt
import com.mkpazon.simpleshiftsapp.data.datasource.remote.Api
import com.mkpazon.simpleshiftsapp.data.datasource.remote.RemoteDataSource
import com.mkpazon.simpleshiftsapp.data.repository.Repository
import com.mkpazon.simpleshiftsapp.di.network.AuthorizationInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val TIMEOUT = 6000L

val appModule = Kodein.Module("appModule") {
    bind<Repository>() with singleton { Repository() }
    bind<Api>() with singleton { getApi(instance()) }
    bind<OkHttpClient>() with provider { getOkhttpClient() }
    bind<RemoteDataSource>() with provider { RemoteDataSource() }
}

private fun getApi(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()
        .create(Api::class.java)

private fun getOkhttpClient() = OkHttpClient.Builder()
        .configEnvt()
        .addInterceptor(AuthorizationInterceptor())
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .build()


