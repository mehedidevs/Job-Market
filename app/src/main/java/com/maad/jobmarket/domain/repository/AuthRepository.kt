package com.maad.jobmarket.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.maad.jobmarket.domain.model.UserAuthModel

interface AuthRepository {
    fun signUp(userAuthModel: UserAuthModel): Task<AuthResult>

    fun signIn(userAuthModel: UserAuthModel): Task<AuthResult>

    fun sendPasswordResetEmail(email: String): Task<Void>

    fun getCurrentUser(): FirebaseUser?
}