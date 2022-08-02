package com.example.viagogodemo.events.model

class GogoEventsWrapper(val id: Int,
                        val name: String,
                        val events: List<Event>?,
                        val children: List<GogoEventsWrapper>?)