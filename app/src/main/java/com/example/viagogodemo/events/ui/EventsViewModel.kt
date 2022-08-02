package com.example.viagogodemo.events.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viagogodemo.events.data.EventsRepository
import com.example.viagogodemo.events.domain.FilterUseCase
import com.example.viagogodemo.events.domain.ListEventUseCase
import com.example.viagogodemo.events.model.Event
import com.example.viagogodemo.events.model.EventFilterCriteria
import com.example.viagogodemo.events.model.GogoEventsWrapper
import com.example.viagogodemo.events.model.Result

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EventsViewModel @Inject constructor(
    private val listEventUseCase: ListEventUseCase,
    private val filterUseCase: FilterUseCase): ViewModel(){


    private val eventLiveData = MutableLiveData<Result<List<Event>?>>()

    private var eventFilterCriteria : EventFilterCriteria? = EventFilterCriteria()

    init {

        syncEvents()
    }

    fun getEventLiveData(): LiveData<Result<List<Event>?>>{
        return eventLiveData
    }


    public fun syncEvents(){
        eventLiveData.value = Result.Loading()

        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            listEventUseCase.getEventsFlow()
                .catch {exception ->
                    eventLiveData.value = Result.Error(exception)
                }
                .collect { events ->
                    eventLiveData.value = Result.Success(events)
                }
        }
    }

    fun applyFilter(eventFilterCriteria: EventFilterCriteria){
        //Update Cached Filter Criteria
        this.eventFilterCriteria = eventFilterCriteria

        eventLiveData.value = Result.Loading()

        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            filterUseCase.filterBy(eventFilterCriteria)
                .catch {exception ->
                    eventLiveData.value = Result.Error(exception)
                }
                .collect { events ->
                    eventLiveData.value = Result.Success(events)
                }
        }

    }

    fun getEventFilterCriteria(): EventFilterCriteria?{
        return eventFilterCriteria
    }

    fun clearFilter(){
        eventFilterCriteria = null

        syncEvents()
    }

}