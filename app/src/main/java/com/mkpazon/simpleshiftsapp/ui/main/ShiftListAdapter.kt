package com.mkpazon.simpleshiftsapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mkpazon.simpleshiftsapp.R
import com.mkpazon.simpleshiftsapp.databinding.ItemShiftBinding
import com.mkpazon.simpleshiftsapp.ui.model.ShiftUi

class ShiftListAdapter : ListAdapter<ShiftUi, ShiftViewHolder>(diffCallback) {
    companion object {
        val diffCallback = object : ItemCallback<ShiftUi>() {
            override fun areItemsTheSame(oldItem: ShiftUi, newItem: ShiftUi) = oldItem === newItem

            override fun areContentsTheSame(oldItem: ShiftUi, newItem: ShiftUi) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShiftViewHolder {
        val binding = DataBindingUtil.inflate<ItemShiftBinding>(LayoutInflater.from(parent.context), R.layout.item_shift, parent, false)
        return ShiftViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShiftViewHolder, position: Int) {
        holder.binding.shift = getItem(position)
    }

}


class ShiftViewHolder(val binding: ItemShiftBinding) : RecyclerView.ViewHolder(binding.root)