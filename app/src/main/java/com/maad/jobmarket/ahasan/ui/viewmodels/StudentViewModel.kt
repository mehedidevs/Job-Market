package com.maad.jobmarket.ahasan.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maad.jobmarket.ahasan.domain.model.Details
import com.maad.jobmarket.ahasan.domain.model.Student
import com.maad.jobmarket.ahasan.domain.repository.StudentRepository
import com.maad.jobmarket.ahasan.domain.usecase.FetchResumeUseCase
import com.maad.jobmarket.ahasan.domain.usecase.GetStudentUseCase
import com.maad.jobmarket.ahasan.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StudentViewModel (
    private val getStudentUseCase: GetStudentUseCase,
    private val fetchResumeUseCase: FetchResumeUseCase,
    private val repository: StudentRepository
) : ViewModel() {

    private val _studentState = MutableStateFlow(StudentDataState())
    val studentState: StateFlow<StudentDataState> get() = _studentState

    private val _resumeState = MutableStateFlow(ResumeDataState())
    val resumeState: StateFlow<ResumeDataState> get() = _resumeState

    init {
        // optional dummy data
        _studentState.value = StudentDataState(
            student = Student(
                uid = "TEST001",
                details = Details(
                    username = "John Doe",
                    email = "john@example.com"
                )
            )
        )
    }

    fun fetchStudent(studentId: String) {
        viewModelScope.launch {
            getStudentUseCase(studentId).collect { resource ->
                _studentState.value = when (resource) {
                    is Resource.Loading -> StudentDataState(loading = true)
                    is Resource.Success -> StudentDataState(student = resource.data)
                    is Resource.Error -> StudentDataState(error = resource.message)
                }
            }
        }
    }

    fun fetchResume(studentId: String) {
        viewModelScope.launch {
            fetchResumeUseCase(studentId).collect { resource ->
                _resumeState.value = when (resource) {
                    is Resource.Loading -> ResumeDataState(loading = true)
                    is Resource.Success -> ResumeDataState(resume = resource.data)
                    is Resource.Error -> ResumeDataState(error = resource.message)
                }
            }
        }
    }

    fun addStudent(student: Student) {
        viewModelScope.launch {
            repository.addStudent(student)
        }
    }
}
