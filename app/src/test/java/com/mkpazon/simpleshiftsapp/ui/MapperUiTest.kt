package com.mkpazon.simpleshiftsapp.ui

import com.mkpazon.simpleshiftsapp.DEFAULT_DATE_FORMAT
import com.mkpazon.simpleshiftsapp.data.model.ShiftApi
import org.junit.Assert

import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*


class MapperUiTest {

    @Test
    fun toShiftUiTest() {
        val shiftApi = ShiftApi(id = 8,
                start = "2019-04-26T00:03:56+1000",
                end = "2019-04-26T00:04:08+1000",
                startLatitude = "-33.7940991",
                startLongitude = "150.9319682",
                endLatitude = "-33.123",
                endLongitude = "150.456",
                image = "https://unsplash.it/500/500?random")
        val shift = MapperUi.toShiftUi(shiftApi)

        Assert.assertEquals(8, shift!!.id)
        Assert.assertEquals(-33.7940991, shift.startCoordinates.lat, 0.0)
        Assert.assertEquals(150.9319682, shift.startCoordinates.lng, 0.0)
        Assert.assertEquals(-33.123, shift.endCoordinates!!.lat, 0.0)
        Assert.assertEquals(150.456, shift.endCoordinates!!.lng, 0.0)
        Assert.assertEquals("https://unsplash.it/500/500?random", shift.image)

        val format = SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault())
        val start = format.parse("2019-04-26T00:03:56+1000")
        val end = format.parse("2019-04-26T00:04:08+1000")

        Assert.assertEquals(start.time, shift.startDate.time)
        Assert.assertEquals(end.time, shift.endDate!!.time)
    }

    @Test
    fun toShiftUiTest_empty_end() {
        val shiftApi = ShiftApi(id = 8,
                start = "2019-04-26T00:03:56+1000",
                end = "",
                startLatitude = "-33.7940991",
                startLongitude = "150.9319682",
                endLatitude = "0.0",
                endLongitude = "0.0",
                image = "https://unsplash.it/500/500?random")
        val shift = MapperUi.toShiftUi(shiftApi)

        Assert.assertEquals(8, shift!!.id)
        Assert.assertEquals(-33.7940991, shift.startCoordinates.lat, 0.0)
        Assert.assertEquals(150.9319682, shift.startCoordinates.lng, 0.0)
        Assert.assertNull(shift.endCoordinates)
        Assert.assertNull(shift.endDate)
        Assert.assertEquals("https://unsplash.it/500/500?random", shift.image)
    }

    @Test
    fun toStartShiftResultUiTest_failResponse(){
        val startShiftResultUi = MapperUi.toStartShiftResultUi("some random text")
        Assert.assertFalse(startShiftResultUi.success)
        Assert.assertEquals("some random text", startShiftResultUi.message)
    }

    @Test
    fun toEndShiftResultUiTest_failResponse(){
        val endShiftResultUi = MapperUi.toEndShiftResultUi("some random text")
        Assert.assertFalse(endShiftResultUi.success)
        Assert.assertEquals("some random text", endShiftResultUi.message)
    }

    @Test
    fun toStartShiftResultUiTest_successResponse(){
        val startShiftResultUi = MapperUi.toStartShiftResultUi("\"Start shift - All good\"")
        Assert.assertTrue(startShiftResultUi.success)
        Assert.assertNull(startShiftResultUi.message)
    }

    @Test
    fun toEndShiftResultUiTest_successResponse(){
        val endShiftResultUi = MapperUi.toEndShiftResultUi("\"End shift - All good\"")
        Assert.assertTrue(endShiftResultUi.success)
        Assert.assertNull(endShiftResultUi.message)
    }
}