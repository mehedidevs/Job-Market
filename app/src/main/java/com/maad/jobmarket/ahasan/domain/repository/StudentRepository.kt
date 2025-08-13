package com.maad.jobmarket.ahasan.domain.repository

import android.net.Uri
import com.maad.jobmarket.ahasan.domain.model.Student
import com.maad.jobmarket.ahasan.utils.Resource

import kotlinx.coroutines.flow.Flow


interface StudentRepository {
    fun getStudent(studentId: String): Flow<Resource<Student>>

    fun fetchResume(studentId: String): Flow<Resource<Triple<String, String, Uri>>>

    suspend fun addStudent(student: Student)
}
