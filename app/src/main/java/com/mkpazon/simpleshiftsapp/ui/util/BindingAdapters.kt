package com.mkpazon.simpleshiftsapp.ui.util

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mkpazon.simpleshiftsapp.util.orFalse

@BindingAdapter("android:visibility")
fun setViewVisibility(view: View, value: Boolean?) {
    view.visibility = if (value.orFalse()) View.VISIBLE else View.GONE
}

@BindingAdapter(value = ["android:src"])
fun setImageSourceUrl(imageView: ImageView, url: String) {
    Glide.with(imageView.context)
            .load(Uri.parse(url))
            .apply(RequestOptions()
                    .centerCrop())
            .into(imageView)
}