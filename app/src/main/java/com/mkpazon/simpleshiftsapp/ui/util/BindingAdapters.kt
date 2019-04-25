package com.mkpazon.simpleshiftsapp.ui.util

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mkpazon.simpleshiftsapp.R
import com.mkpazon.simpleshiftsapp.ui.model.ShiftUi
import com.mkpazon.simpleshiftsapp.util.orFalse
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

private const val DATE_FORMAT_TIME_ONLY = "HH:mma"
private const val DATE_FORMAT_MONTH_ONLY = "MMM"
private const val DATE_FORMAT_DAY_OF_MONTH_ONLY = "dd"

@BindingAdapter("android:visibility")
fun setViewVisibility(view: View, value: Boolean?) {
    view.visibility = if (value.orFalse()) View.VISIBLE else View.GONE
}

@BindingAdapter("android:src")
fun setImageSourceUrl(imageView: ImageView, url: String) {
    Glide.with(imageView.context)
            .load(Uri.parse(url))
            .apply(RequestOptions()
                    .centerCrop())
            .into(imageView)
}

@BindingAdapter("shiftTime")
fun setShiftTime(textView: TextView, shift: ShiftUi) {
    if (shift.endDate != null) {
        val format = SimpleDateFormat(DATE_FORMAT_TIME_ONLY, Locale.getDefault())
        val startTime = format.format(shift.startDate)
        val endTime = format.format(shift.endDate)

        val startCal = Calendar.getInstance().apply {
            timeInMillis = shift.startDate.time
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val endCal = Calendar.getInstance().apply {
            timeInMillis = shift.endDate.time
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val difference = endCal.timeInMillis - startCal.timeInMillis
        val addDays = TimeUnit.MILLISECONDS.toDays(difference).toInt()
        val text = if (addDays > 0) {
            "$startTime - $endTime +$addDays"
        } else if (addDays < 0) {
            "$startTime - $endTime $addDays"
        } else {
            "$startTime - $endTime"
        }
        textView.text = text
    } else {
        textView.text = textView.context.getString(R.string.in_progress)
    }
}

@BindingAdapter("dayOfMonth")
fun setDayOfMonth(textView: TextView, date: Date) {
    val format = SimpleDateFormat(DATE_FORMAT_DAY_OF_MONTH_ONLY, Locale.getDefault())
    textView.text = format.format(date)
}

@BindingAdapter("month")
fun setMonth(textView: TextView, date: Date) {
    val format = SimpleDateFormat(DATE_FORMAT_MONTH_ONLY, Locale.getDefault())
    textView.text = format.format(date)
}

