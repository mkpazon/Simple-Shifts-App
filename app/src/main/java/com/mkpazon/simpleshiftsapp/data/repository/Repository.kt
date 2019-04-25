package com.mkpazon.simpleshiftsapp.data.repository

import com.mkpazon.simpleshiftsapp.App
import com.mkpazon.simpleshiftsapp.data.datasource.remote.RemoteDataSource
import com.mkpazon.simpleshiftsapp.data.model.ShiftRequest
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class Repository : KodeinAware {

    override val kodein by kodein(App.context)

    private val remoteDataSource: RemoteDataSource by instance()

    fun getShiftsAsync() = remoteDataSource.getShiftsAsync()

    fun startShiftAsync(request: ShiftRequest) = remoteDataSource.startShiftAsync(request)

    fun endShiftAsync(request: ShiftRequest) = remoteDataSource.endShiftAsync(request)

}