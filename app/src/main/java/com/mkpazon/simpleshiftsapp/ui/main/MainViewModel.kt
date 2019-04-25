package com.mkpazon.simpleshiftsapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkpazon.simpleshiftsapp.App
import com.mkpazon.simpleshiftsapp.data.repository.Repository
import com.mkpazon.simpleshiftsapp.ui.MapperUi
import com.mkpazon.simpleshiftsapp.ui.Resource
import com.mkpazon.simpleshiftsapp.ui.model.ShiftUi
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
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

    fun getShifts() = scope.launch {
        shiftsLiveData.value = Resource.loading()
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val shifts = repository.getShiftsAsync().await()
                val shiftsUi = shifts?.mapNotNull { MapperUi.toShiftUi(it) }
                shiftsLiveData.postValue(Resource.success(shiftsUi))
            } catch (e: Exception) {
                shiftsLiveData.postValue(Resource.error(e))
            }
        }
    }

    fun startShift() = runBlocking {
        // TODO not yet implemented
    }

    fun endShift() = runBlocking {
        // TODO not yet implemented
    }
}
