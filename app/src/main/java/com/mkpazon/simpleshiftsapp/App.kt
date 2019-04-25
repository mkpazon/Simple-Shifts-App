package com.mkpazon.simpleshiftsapp

import android.app.Application
import com.facebook.stetho.Stetho
import com.mkpazon.simpleshiftsapp.di.appModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class App : Application(), KodeinAware {
    override val kodein = Kodein.lazy { import(appModule) }

    companion object {
        lateinit var context: App
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        Stetho.initializeWithDefaults(this)
    }
}