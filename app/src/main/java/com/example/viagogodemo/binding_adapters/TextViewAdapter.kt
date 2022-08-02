package com.example.viagogodemo.binding_adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.viagogodemo.R
import com.example.viagogodemo.extentions.formatToPrice


@BindingAdapter("app:setTextPrice")
fun setTextPrice(textView: TextView, textPrice : Int?){
   textPrice?.let {
       textView.text = it.formatToPrice()
   }

}