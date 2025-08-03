package com.maad.jobmarket.presentation.startNav

import com.google.firebase.auth.AuthResult

data class AuthDataState(
    val loading: Boolean = false,
    val data: AuthResult? = null,
    val error: String? = null
)
