package com.mkpazon.simpleshiftsapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkpazon.simpleshiftsapp.App
import com.mkpazon.simpleshiftsapp.data.model.ShiftRequest
import com.mkpazon.simpleshiftsapp.data.repository.Repository
import com.mkpazon.simpleshiftsapp.ui.MapperUi
import com.mkpazon.simpleshiftsapp.ui.Resource
import com.mkpazon.simpleshiftsapp.ui.model.EndShiftResult
import com.mkpazon.simpleshiftsapp.ui.model.ShiftUi
import com.mkpazon.simpleshiftsapp.ui.model.StartShiftResult
import com.mkpazon.simpleshiftsapp.util.SingleLiveEvent
import com.mkpazon.simpleshiftsapp.util.format
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.*
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel(), KodeinAware {
    private val job = Job()

    private val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    override val kodein by kodein(App.context)
    private val repository by instance<Repository>()

    private val shiftsLiveData = MutableLiveData<Resource<List<ShiftUi>>>()
    val shifts: LiveData<Resource<List<ShiftUi>>> = shiftsLiveData

    private val startShiftLiveData = SingleLiveEvent<Resource<StartShiftResult>>()
    val startShift: SingleLiveEvent<Resource<StartShiftResult>> = startShiftLiveData

    private val endShiftLiveData = SingleLiveEvent<Resource<EndShiftResult>>()
    val endShift: SingleLiveEvent<Resource<EndShiftResult>> = endShiftLiveData

    fun getShifts() = scope.launch {
        shiftsLiveData.value = Resource.loading()
        launch(Dispatchers.IO) {
            try {
                val shifts = repository.getShiftsAsync().await()
                val shiftsUi = shifts?.mapNotNull { MapperUi.toShiftUi(it) }?.sortedBy { it.startDate }
                shiftsLiveData.postValue(Resource.success(shiftsUi))
            } catch (e: Exception) {
                shiftsLiveData.postValue(Resource.error(e))
            }
        }
    }

    fun startShift(latitude: Double, longitude: Double) = scope.launch {
        startShiftLiveData.value = Resource.loading()
        launch(Dispatchers.IO) {
            try {
                val shiftRequest = ShiftRequest(Calendar.getInstance().format(), latitude.toString(), longitude.toString())
                val response = repository.startShiftAsync(shiftRequest).await()
                startShiftLiveData.postValue(Resource.success(MapperUi.toStartShiftResultUi(response)))
            } catch (e: Exception) {
                startShiftLiveData.postValue(Resource.error(e))
            }
        }
    }

    fun endShift(latitude: Double, longitude: Double) = scope.launch {
        endShiftLiveData.value = Resource.loading()
        launch(Dispatchers.IO) {
            try {
                val shiftRequest = ShiftRequest(Calendar.getInstance().format(), latitude.toString(), longitude.toString())
                val response = repository.endShiftAsync(shiftRequest).await()
                endShiftLiveData.postValue(Resource.success(MapperUi.toEndShiftResultUi(response)))
            } catch (e: Exception) {
                endShiftLiveData.postValue(Resource.error(e))
            }
        }
    }
}
