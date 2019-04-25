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
import java.util.*

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
        val shift = getItem(position)
        holder.binding.shift = shift
        holder.binding.shouldShowDate = !(position > 0 && startsOnSameDay(getItem(position - 1), shift))
    }

    private fun startsOnSameDay(shift1: ShiftUi, shift2: ShiftUi): Boolean {
        val start1 = Calendar.getInstance().apply { time = shift1.startDate }
        val start2 = Calendar.getInstance().apply { time = shift2.startDate }
        return start1.get(Calendar.MONTH) == start2.get(Calendar.MONTH) &&
                start1.get(Calendar.DAY_OF_MONTH) == start2.get(Calendar.DAY_OF_MONTH) &&
                start1.get(Calendar.YEAR) == start2.get(Calendar.YEAR)
    }
}


class ShiftViewHolder(val binding: ItemShiftBinding) : RecyclerView.ViewHolder(binding.root)