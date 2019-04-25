package com.mkpazon.simpleshiftsapp.ui

import com.mkpazon.simpleshiftsapp.data.model.ShiftApi
import com.mkpazon.simpleshiftsapp.ui.model.Coordinates
import com.mkpazon.simpleshiftsapp.ui.model.EndShiftResult
import com.mkpazon.simpleshiftsapp.ui.model.ShiftUi
import com.mkpazon.simpleshiftsapp.ui.model.StartShiftResult
import com.mkpazon.simpleshiftsapp.util.toDate
import timber.log.Timber

class MapperUi {
    companion object {
        private const val START_SHIFT_SUCCESS = "\"Start shift - All good\""
        private const val END_SHIFT_SUCCESS = "\"End shift - All good\""

        fun toShiftUi(shiftApi: ShiftApi): ShiftUi? {
            return try {
                ShiftUi(id = shiftApi.id,
                        startDate = shiftApi.start.toDate(),
                        endDate = if (shiftApi.end.isEmpty()) null else shiftApi.end.toDate(),
                        startCoordinates = Coordinates(shiftApi.startLatitude.toDouble(), shiftApi.startLongitude.toDouble()),
                        endCoordinates = if(shiftApi.end.isEmpty()) null else Coordinates(shiftApi.endLatitude.toDouble(), shiftApi.endLongitude.toDouble()),
                        image = shiftApi.image
                )
            } catch (e: Exception) {
                // If one item fails to be mapped, just log and not let the whole list fail
                Timber.w(e, "Failed to map Shift Api to Shift Ui")
                null
            }
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