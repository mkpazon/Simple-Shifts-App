package com.mkpazon.simpleshiftsapp.ui

import com.mkpazon.simpleshiftsapp.data.model.ShiftApi
import com.mkpazon.simpleshiftsapp.ui.model.Coordinates
import com.mkpazon.simpleshiftsapp.ui.model.ShiftUi
import com.mkpazon.simpleshiftsapp.util.toDate

class MapperUi {
    companion object {
        fun toShiftUi(shiftApi: ShiftApi): ShiftUi {
            return ShiftUi(id = shiftApi.id,
                    startDate = shiftApi.start.toDate(),
                    endDate = shiftApi.end.toDate(),
                    startCoordinates = Coordinates(shiftApi.startLatitude.toDouble(), shiftApi.startLongitude.toDouble()),
                    endCoordinates = Coordinates(shiftApi.endLatitude.toDouble(), shiftApi.endLongitude.toDouble()),
                    image = shiftApi.image
            )
        }
    }
}