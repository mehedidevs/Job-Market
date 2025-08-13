package com.maad.jobmarket.home_notification_Nahid.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maad.jobmarket.home_notification_Nahid.data.model.Job

class JobRepository {

        fun getJobs(): LiveData<List<Job>> {
        val jobList = MutableLiveData<List<Job>>()
        jobList.value = listOf(
            Job("1", "Android Developer", "Google", "USA", "1 day ago", ""),
            Job("2", "Backend Engineer", "Meta", "Germany", "2 days ago", "")
        )
        return jobList
    }
}