package com.maad.jobmarket.presentation.startNav.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maad.jobmarket.core.utils.Resource
import com.maad.jobmarket.domain.model.UserAuthModel
import com.maad.jobmarket.domain.use_case.auth_usecase.SignInUseCase
import com.maad.jobmarket.presentation.startNav.AuthDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
): ViewModel() {
    private val _signInState = MutableStateFlow(AuthDataState())
    val signInState: StateFlow<AuthDataState> get() = _signInState

    // SignIn function
    fun signIn(userAuthModel: UserAuthModel) {
        viewModelScope.launch {
            signInUseCase(userAuthModel).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _signInState.value = AuthDataState(loading = true)
                    }
                    is Resource.Success -> {
                        _signInState.value = AuthDataState(data = resource.data)
                    }
                    is Resource.Error -> {
                        _signInState.value = AuthDataState(error = resource.message)
                    }
                }
            }
        }
    }

}