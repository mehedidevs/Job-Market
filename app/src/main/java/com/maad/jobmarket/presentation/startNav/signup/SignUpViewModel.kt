package com.maad.jobmarket.presentation.startNav.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maad.jobmarket.core.utils.Resource
import com.maad.jobmarket.domain.model.UserAuthModel
import com.maad.jobmarket.domain.use_case.auth_usecase.SignUpUseCase
import com.maad.jobmarket.presentation.startNav.AuthDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
): ViewModel(){

    private val _signUpState = MutableStateFlow(AuthDataState())
    val signUpState: StateFlow<AuthDataState> get() = _signUpState

    // SignUp function
    fun signUp(userAuthModel: UserAuthModel) {
        viewModelScope.launch {
            signUpUseCase(userAuthModel).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _signUpState.value = AuthDataState(loading = true)
                    }
                    is Resource.Success -> {
                        _signUpState.value = AuthDataState(data = resource.data)
                    }
                    is Resource.Error -> {
                        _signUpState.value = AuthDataState(error = resource.message)
                    }
                }
            }
        }
    }
}