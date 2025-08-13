package com.maad.jobmarket.ahasan.data.repository

import android.net.Uri
import com.maad.jobmarket.ahasan.data.remote.database.FirebaseStudentService
import com.maad.jobmarket.ahasan.domain.model.Student
import com.maad.jobmarket.ahasan.domain.repository.StudentRepository
import com.maad.jobmarket.ahasan.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

abstract class StudentRepositoryImpl  (private val remote: FirebaseStudentService) : StudentRepository {

      override fun getStudent(studentId: String): Flow<Resource<Student>> = flow {
        emit(Resource.Loading())
        try {
            val student = remote.getStudent(studentId)
            emit(Resource.Success(student))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown Error"))
        }
      }

      override fun fetchResume(studentId: String): Flow<Resource<Triple<String, String, Uri>>> = flow {
        emit(Resource.Loading())
        try {
            val resume = remote.fetchResume(studentId)
            emit(Resource.Success(resume))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown Error"))
        }
    }

     override suspend fun addStudent(student: Student) {
        remote.addStudent(student)
    }
}

