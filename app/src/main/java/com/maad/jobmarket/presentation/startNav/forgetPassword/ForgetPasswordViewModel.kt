package com.maad.jobmarket.presentation.startNav.forgetPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maad.jobmarket.core.utils.Resource
import com.maad.jobmarket.domain.use_case.auth_usecase.ResetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
): ViewModel() {
    private val _resetPasswordState = MutableStateFlow(RestPasswordDataState())
    val resetPasswordState: StateFlow<RestPasswordDataState> get() = _resetPasswordState

    // ResetPassword function
    fun resetPassword(email: String) {
        viewModelScope.launch {
            resetPasswordUseCase(email).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _resetPasswordState.value = RestPasswordDataState(loading = true)
                    }
                    is Resource.Success -> {
                        _resetPasswordState.value = RestPasswordDataState(data = resource.data)
                    }
                    is Resource.Error -> {
                        _resetPasswordState.value = RestPasswordDataState(error = resource.message)
                    }
                }
            }
        }
    }
}