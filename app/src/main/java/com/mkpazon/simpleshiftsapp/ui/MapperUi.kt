package com.mkpazon.simpleshiftsapp.ui

import com.mkpazon.simpleshiftsapp.data.model.ShiftApi
import com.mkpazon.simpleshiftsapp.ui.model.Coordinates
import com.mkpazon.simpleshiftsapp.ui.model.EndShiftResult
import com.mkpazon.simpleshiftsapp.ui.model.ShiftUi
import com.mkpazon.simpleshiftsapp.ui.model.StartShiftResult
import com.mkpazon.simpleshiftsapp.util.toDate

class MapperUi {
    companion object {
        private const val START_SHIFT_SUCCESS = "Start shift - All good"
        private const val END_SHIFT_SUCCESS = "End shift - All good"

        fun toShiftUi(shiftApi: ShiftApi): ShiftUi {
            return ShiftUi(id = shiftApi.id,
                    startDate = shiftApi.start.toDate(),
                    endDate = shiftApi.end.toDate(),
                    startCoordinates = Coordinates(shiftApi.startLatitude.toDouble(), shiftApi.startLongitude.toDouble()),
                    endCoordinates = Coordinates(shiftApi.endLatitude.toDouble(), shiftApi.endLongitude.toDouble()),
                    image = shiftApi.image
            )
        }

        fun toStartShiftResultUi(response: String) = when (response) {
            START_SHIFT_SUCCESS -> StartShiftResult(true, null)
            else -> StartShiftResult(false, response)
        }

        fun toEndShiftResultUi(response: String) = when (response) {
            END_SHIFT_SUCCESS -> EndShiftResult(true, null)
            else -> EndShiftResult(false, response)
        }
    }
}