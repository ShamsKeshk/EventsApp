package com.example.viagogodemo.extentions

import java.text.NumberFormat
import java.util.*


fun Int.formatToPrice(): String{
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.currency = Currency.getInstance("EUR")

    return format.format(this);
}