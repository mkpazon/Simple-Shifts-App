package com.mkpazon.simpleshiftsapp.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationServices

import com.mkpazon.simpleshiftsapp.R
import com.mkpazon.simpleshiftsapp.databinding.FragmentMainBinding
import com.mkpazon.simpleshiftsapp.ui.Resource.Status.*
import com.mkpazon.simpleshiftsapp.ui.util.toast
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var shiftAdapter: ShiftListAdapter

    private var menuStart: MenuItem? = null
    private var menuEnd: MenuItem? = null

    companion object {
        const val MY_PERMISSIONS_LOCATION = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        initUi()
        viewModel.getShifts()
    }

    private fun initUi() {
        with(rv_shifts) {
            layoutManager = LinearLayoutManager(activity)
            shiftAdapter = ShiftListAdapter()
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            adapter = shiftAdapter
        }

        sw_shifts.setOnRefreshListener {
            viewModel.getShifts()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
        menuStart = menu.findItem(R.id.menu_start_shift)
        menuEnd = menu.findItem(R.id.menu_end_shift)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_start_shift -> {
                performStartShift()
                true
            }
            R.id.menu_end_shift -> {
                performEndShift()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun performEndShift() = CoroutineScope(Dispatchers.Main).launch {
        checkLocationServiceAndPermission()
        val location = getLocation()
        location?.let { viewModel.endShift(it.latitude, it.longitude) }
    }

    private fun performStartShift() = CoroutineScope(Dispatchers.Main).launch {
        checkLocationServiceAndPermission()
        val location = getLocation()
        location?.let { viewModel.startShift(it.latitude, it.longitude) }
    }

    private suspend fun getLocation(): Location? = suspendCoroutine { cont ->
        activity?.let {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(it)
            fusedLocationClient.lastLocation.addOnSuccessListener {
                cont.resume(it)
            }.addOnFailureListener {
                cont.resume(null)
            }
        }
    }

    private fun checkLocationServiceAndPermission() {
        activity?.let { activity ->
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // TODO
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                            MY_PERMISSIONS_LOCATION)
                }
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java).apply {
            shifts.observe(this@MainFragment, Observer { resource ->
                when (resource.status) {
                    LOADING -> {
                        binding.isLoading = true
                        refreshMenuVisibility()
                    }
                    SUCCESS -> {
                        binding.isLoading = false
                        resource.data?.let { shiftAdapter.submitList(it) }
                        refreshMenuVisibility()
                    }
                    ERROR -> {
                        binding.isLoading = false
                        refreshMenuVisibility()

                        Timber.e(resource.exception, "Failed to retrieve shifts")
                    }
                }
            })

            startShift.observe(this@MainFragment, Observer { resource ->
                when (resource.status) {
                    LOADING -> binding.isLoading = true
                    SUCCESS -> {
                        binding.isLoading = false
                        resource.data?.let {
                            when {
                                it.success -> {
                                    toast(getString(R.string.shift_start_success))
                                    viewModel.getShifts()
                                }
                                else -> it.message?.let { toast(it) }
                            }
                        }
                    }
                    ERROR -> {
                        binding.isLoading = false
                        Timber.e(resource.exception, "Failed to start shift ")
                    }
                }
            })

            endShift.observe(this@MainFragment, Observer { resource ->
                when (resource.status) {
                    LOADING -> binding.isLoading = true
                    SUCCESS -> {
                        binding.isLoading = false
                        resource.data?.let {
                            when {
                                it.success -> {
                                    toast(getString(R.string.shift_end_success))
                                    viewModel.getShifts()
                                }
                                else -> it.message?.let { toast(it) }
                            }
                        }
                    }
                    ERROR -> {
                        binding.isLoading = false
                        Timber.e(resource.exception, "Failed to end shift ")
                    }
                }
            })
        }
    }

    private fun refreshMenuVisibility() {
        val allCompleted = shiftAdapter.allCompleted()

        when {
            binding.isLoading || allCompleted == null -> {
                menuStart?.isVisible = false
                menuEnd?.isVisible = false
            }
            allCompleted -> {
                menuStart?.isVisible = true
                menuEnd?.isVisible = false
            }
            else -> {
                menuStart?.isVisible = false
                menuEnd?.isVisible = true
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    toast(getString(R.string.location_permission_disabled_message))
                }
                return
            }
        }
    }
}
