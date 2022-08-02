package com.example.viagogodemo.events.domain

import com.example.viagogodemo.events.data.EventsRepository
import com.example.viagogodemo.events.model.Event
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class ListEventUseCase @Inject constructor(private val repository: EventsRepository) {

    fun getEventsFlow(): Flow<List<Event>?> {
        return repository.eventsFlow
    }
}