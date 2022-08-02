package com.example.viagogodemo.binding_adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.viagogodemo.R

@BindingAdapter("app:loadImageUrl")
fun loadImageUrl(imageView: ImageView, imageUrl : String?){
    Glide.with(imageView.context)
        .load(imageUrl)
        .placeholder(R.drawable.iv_event_default_name)
        .error(R.drawable.iv_event_default_name)
        .into(imageView)

}