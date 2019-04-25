package com.mkpazon.simpleshiftsapp.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.mkpazon.simpleshiftsapp.R
import com.mkpazon.simpleshiftsapp.databinding.FragmentMainBinding
import com.mkpazon.simpleshiftsapp.ui.Resource.Status.*
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var shiftAdapter: ShiftListAdapter

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
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java).apply {
            shifts.observe(this@MainFragment, Observer { resource ->
                when (resource.status) {
                    LOADING -> binding.isLoading = true
                    SUCCESS -> {
                        resource.data?.let { shiftAdapter.submitList(it) }
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
