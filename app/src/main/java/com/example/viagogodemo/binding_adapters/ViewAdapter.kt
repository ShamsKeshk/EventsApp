package com.example.viagogodemo.binding_adapters

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("app:visibleGone")
fun visibleGone(view: View, enable : Boolean){
    when {
        enable -> {
            view.visibility = View.VISIBLE
        }
        else -> {
            view.visibility = View.GONE
        }
    }
}