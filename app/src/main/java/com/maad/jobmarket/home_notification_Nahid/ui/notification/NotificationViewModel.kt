package com.maad.jobmarket.home_notification_Nahid.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.maad.jobmarket.home_notification_Nahid.data.model.Notification
import com.maad.jobmarket.home_notification_Nahid.data.repository.NotificationRepo

class NotificationViewModel(val repository: NotificationRepo) : ViewModel() {

    val notifications : LiveData<List<Notification>> = repository.getNotifications()

}