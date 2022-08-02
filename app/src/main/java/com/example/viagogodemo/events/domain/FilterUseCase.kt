package com.example.viagogodemo.events.domain

import com.example.viagogodemo.events.data.EventsRepository
import com.example.viagogodemo.events.model.Event
import com.example.viagogodemo.events.model.EventFilterCriteria
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class FilterUseCase @Inject constructor(private val repository: EventsRepository) {

    fun filterBy(eventFilterCriteria: EventFilterCriteria): Flow<List<Event>?> {
        return repository.eventsFlow
            .transform {
                emit(filterEvents(eventFilterCriteria,it))
            }.flowOn(Dispatchers.IO)

    }


    private fun filterEvents(eventFilterCriteria: EventFilterCriteria,events: List<Event>?): List<Event>? {
        if (events.isNullOrEmpty())
            return null

        return events.filter {
            if (eventFilterCriteria.isContainsCityFilterCriteria()) {
                isMatchCityCriteria(eventFilterCriteria,it)
            }else {
                true
            }
        }.filter {
            if (eventFilterCriteria.isContainsPriceFilterCriteria()) {
                isMatchPriceCriteria(eventFilterCriteria,it)
            }else {
                true
            }
        }
    }

    private fun isMatchCityCriteria(eventFilterCriteria: EventFilterCriteria,event: Event): Boolean{
        return event.city.contains(eventFilterCriteria.cityQuery.toString(),true)
    }

    private fun isMatchPriceCriteria(eventFilterCriteria: EventFilterCriteria,event: Event): Boolean{
        return event.price <= eventFilterCriteria.price
    }
}