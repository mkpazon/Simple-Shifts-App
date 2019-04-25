package com.mkpazon.simpleshiftsapp.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels

import com.mkpazon.simpleshiftsapp.R
import com.mkpazon.simpleshiftsapp.databinding.FragmentMainBinding
import com.mkpazon.simpleshiftsapp.ui.Resource
import com.mkpazon.simpleshiftsapp.ui.Resource.Status.*
import timber.log.Timber

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()

        viewModel.getShifts()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java).apply {
            shifts.observe(this@MainFragment, Observer { resource ->
                when (resource.status) {
                    LOADING -> {
                        binding.isLoading = true
                    }
                    SUCCESS -> {
                        binding.isLoading = false
                    }
                    ERROR -> {
                        binding.isLoading = false
                        Timber.e(resource.exception, "Failed to retrieve shifts")
                    }
                }
            })
        }
    }
}
