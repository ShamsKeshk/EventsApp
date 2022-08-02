package com.example.viagogodemo.events.data.utils

import android.app.Application
import androidx.annotation.WorkerThread

class FileHelper {

    companion object{

        @WorkerThread
        fun getJsonAsTextFromAssets(application: Application,fileName: String) : String{
            return application.assets.open(fileName).use { inputStream ->
                inputStream.bufferedReader().use {
                    it.readText().replace("\n","")
                }
            }
        }
    }
}