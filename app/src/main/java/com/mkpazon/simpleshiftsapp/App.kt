package com.mkpazon.simpleshiftsapp

import android.app.Application
import com.facebook.stetho.Stetho
import com.mkpazon.simpleshiftsapp.di.appModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import timber.log.Timber



class App : Application(), KodeinAware {
    override val kodein = Kodein.lazy { import(appModule) }

    companion object {
        lateinit var context: App
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        initTimber()
        Stetho.initializeWithDefaults(this)
    }

    private fun initTimber() {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String? {
                return String.format("C:%s:%s",
                        super.createStackElementTag(element),
                        element.lineNumber)
            }
        })
    }
}