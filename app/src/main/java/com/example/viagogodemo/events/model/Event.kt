package com.example.viagogodemo.events.model

class Event(val id: Int,
            val name: String,
            val date: String,
            val venueName: String,
            val url: String?,
            val city: String,
            val price: Int,
            val distanceFromVenue: Double) {

}