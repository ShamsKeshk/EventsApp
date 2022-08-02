package com.example.viagogodemo.events.data.local

import android.app.Application
import com.example.viagogodemo.events.data.utils.FileHelper
import com.example.viagogodemo.events.model.GogoEventsWrapper
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val CACHED_EVENTS_DATA_FILE_NAME = "ViaGogoEvents.json"

class GogoEventLocalDataSource @Inject constructor(private val application: Application) {

    val gogoEventsWrapperFlow: Flow<GogoEventsWrapper?> = flow {

        val jsonText: String = FileHelper.getJsonAsTextFromAssets(application, CACHED_EVENTS_DATA_FILE_NAME)

        if (jsonText.isNullOrEmpty()) {
            emit(null)
        }else {
            val eventsWrapper = Gson().fromJson(jsonText, GogoEventsWrapper::class.java)
            emit(eventsWrapper)
        }
    }.flowOn(Dispatchers.IO)
}