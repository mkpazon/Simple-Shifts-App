package com.mkpazon.simpleshiftsapp.data.datasource.remote

import com.mkpazon.simpleshiftsapp.data.model.*
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api  {

    @GET("shifts")
    fun getShiftsAsync(): Deferred<List<ShiftApi>?>

    @POST("shift/start")
    fun startShiftAsync(@Body request: ShiftRequest): Deferred<ShiftResponse>

    @POST("shift/end")
    fun endShiftAsync(@Body request: ShiftRequest): Deferred<ShiftResponse>

}