package com.mkpazon.simpleshiftsapp.data.datasource.remote

import com.mkpazon.simpleshiftsapp.App
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class RemoteDataSource : KodeinAware {

    override val kodein by kodein(App.context)

    private val api: Api by instance()

}