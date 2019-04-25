package com.mkpazon.simpleshiftsapp.ui.model

import java.util.*

data class ShiftUi(val id: Int,
                   val startDate: Date,
                   val endDate: Date,
                   val startCoordinates: Coordinates,
                   val endCoordinates: Coordinates,
                   val image: String)

data class Coordinates(val lat: Double, val lng: Double)