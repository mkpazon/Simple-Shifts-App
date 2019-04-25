package com.mkpazon.simpleshiftsapp.data.model

import com.squareup.moshi.Json

data class ShiftApi(@Json(name = "id")
                    val id: Int,
                    @Json(name = "start")
                    val start: String,
                    @Json(name = "end")
                    val end: String,
                    @Json(name = "startLatitude")
                    val startLatitude: String,
                    @Json(name = "startLongitude")
                    val startLongitude: String,
                    @Json(name = "endLatitude")
                    val endLatitude: String,
                    @Json(name = "endLongitude")
                    val endLongitude: String,
                    @Json(name = "image")
                    val image: String)