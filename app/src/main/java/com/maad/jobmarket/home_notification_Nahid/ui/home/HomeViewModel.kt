package com.maad.jobmarket.home_notification_Nahid.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maad.jobmarket.home_notification_Nahid.data.model.Job
import com.maad.jobmarket.home_notification_Nahid.data.repository.JobRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel (val repository: JobRepository): ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val jobs: LiveData<List<Job>> = repository.getJobs()

    fun loadJobs (){

        isLoading.value = true
        viewModelScope.launch {
            delay(1500)
            isLoading.value = false
        }

    }

}