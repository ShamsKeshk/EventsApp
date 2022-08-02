package com.example.viagogodemo.events.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.example.viagogodemo.events.data.local.GogoEventLocalDataSource
import com.example.viagogodemo.events.model.Event
import com.example.viagogodemo.events.model.GogoEventsWrapper
import com.example.viagogodemo.events.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventsRepository @Inject constructor(
    private val eventsLocalDataSource: GogoEventLocalDataSource) {

    private val eventLiveData = MutableLiveData<Result<GogoEventsWrapper>>()
    val eventsFlow : Flow<List<Event>?> = eventsLocalDataSource.gogoEventsWrapperFlow
        .transform {
            emit(flatEventWrapperToEvents(it))
        }.flowOn(Dispatchers.IO)

    init {
//        eventLiveData.value = Result.Loading()
//
//        CoroutineScope(Dispatchers.IO).launch {
//            syncEventsWrapper()
//        }
    }

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

//    @WorkerThread
//    suspend fun syncEventsWrapper(){
//        try {
//            val eventList = eventsLocalDataSource.getGogoEventsWrapper()
//
//            eventLiveData.postValue(Result.Success(eventList))
//        }catch (ex: Exception){
//            eventLiveData.postValue(Result.Error(ex))
//        }
//    }

}