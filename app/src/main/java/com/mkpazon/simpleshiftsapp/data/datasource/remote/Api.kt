package com.mkpazon.simpleshiftsapp.data.datasource.remote

import com.mkpazon.simpleshiftsapp.data.model.GetShiftsResponse
import com.mkpazon.simpleshiftsapp.data.model.ShiftApi
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface Api  {

    @GET("shifts")
    fun getShiftsAsync(): Deferred<List<ShiftApi>?>

}