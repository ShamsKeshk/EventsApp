package com.example.viagogodemo.events.data

import com.example.viagogodemo.events.data.local.GogoEventLocalDataSource
import com.example.viagogodemo.events.model.Event
import com.example.viagogodemo.events.model.GogoEventsWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventsRepository @Inject constructor(eventsLocalDataSource: GogoEventLocalDataSource) {

    val eventsFlow : Flow<List<Event>?> = eventsLocalDataSource.gogoEventsWrapperFlow
        .transform {
            emit(flatEventWrapperToEvents(it))
        }.flowOn(Dispatchers.IO)

    private suspend fun flatEventWrapperToEvents(eventsWrapper: GogoEventsWrapper?): List<Event>? {
        if (eventsWrapper == null)
            return null

        val eventsList = mutableListOf<Event>()

        addEventsFromWrapper(eventsWrapper,eventsList)

        return eventsList
    }

    private fun addEventsFromWrapper(gogoEventsWrapper: GogoEventsWrapper,eventsList: MutableList<Event>){
        if (gogoEventsWrapper.events?.isNotEmpty() == true){
            eventsList.addAll(gogoEventsWrapper.events)
        }else {
            gogoEventsWrapper.children?.forEach{
                addEventsFromWrapper(it,eventsList)
            }
        }
    }
}