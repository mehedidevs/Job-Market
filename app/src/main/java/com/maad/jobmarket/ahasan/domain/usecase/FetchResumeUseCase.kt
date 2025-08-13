package com.maad.jobmarket.ahasan.domain.usecase

import android.net.Uri
import com.maad.jobmarket.ahasan.domain.repository.StudentRepository
import com.maad.jobmarket.ahasan.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FetchResumeUseCase @Inject constructor (private val repository: StudentRepository) {
    operator fun invoke(studentId: String): Flow<Resource<Triple<String, String, Uri>>> =
        repository.fetchResume(studentId)
}