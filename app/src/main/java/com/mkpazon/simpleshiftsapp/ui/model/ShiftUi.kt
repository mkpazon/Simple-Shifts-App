package com.mkpazon.simpleshiftsapp.ui.model

import java.util.*

data class ShiftUi(val id: Int,
                   val startDate: Date,
                   val endDate: Date?,
                   val startCoordinates: Coordinates,
                   val endCoordinates: Coordinates,
                   val image: String) {
    fun getStatus(): ShiftStatus {
        return if (endDate != null) {
            ShiftStatus.COMPLETED
        } else {
            ShiftStatus.STARTED
        }
    }
}

data class Coordinates(val lat: Double, val lng: Double)

enum class ShiftStatus {
    STARTED, COMPLETED
}