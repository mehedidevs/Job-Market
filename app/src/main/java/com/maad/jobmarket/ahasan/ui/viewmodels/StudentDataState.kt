package com.maad.jobmarket.ahasan.ui.viewmodels

import android.net.Uri
import com.maad.jobmarket.ahasan.domain.model.Student

data class StudentDataState(
    val loading: Boolean = false,
    val student: Student? = null,
    val error: String? = null
)
data class ResumeDataState(
    val loading: Boolean = false,
    val resume: Triple<String, String, Uri>? = null,
    val error: String? = null
)
