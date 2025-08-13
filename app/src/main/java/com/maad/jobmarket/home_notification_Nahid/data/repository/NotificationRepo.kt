package com.maad.jobmarket.home_notification_Nahid.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maad.jobmarket.home_notification_Nahid.data.model.Notification

class NotificationRepo {

    fun getNotifications(): LiveData<List<Notification>>{
    val liveData= MutableLiveData<List<Notification>>()
        liveData.value = listOf(
            Notification("Congratulations!","You were selected for the job","Just now"),
            Notification("Interview Scheduled","Check your email for details","1 hour ago"),
            Notification("New Job Alert","Backend developer job posted","2 days ago"),

        )
        return liveData

    }

}