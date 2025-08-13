package com.maad.jobmarket.ahasan.domain.usecase

import com.maad.jobmarket.ahasan.domain.repository.StudentRepository
import javax.inject.Inject

class GetStudentUseCase @Inject constructor (private val repository: StudentRepository) {
    suspend operator fun invoke(studentId: String) = repository.getStudent(studentId)
}

