package com.maad.jobmarket.data.repositoryImpl

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.maad.jobmarket.data.remote.FirebaseService
import com.maad.jobmarket.domain.model.UserAuthModel
import com.maad.jobmarket.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseService: FirebaseService
) : AuthRepository {

    override fun signUp(userAuthModel: UserAuthModel): Task<AuthResult> {
        return firebaseService.signUp(userAuthModel)
    }

    override fun signIn(userAuthModel: UserAuthModel): Task<AuthResult> {
        return firebaseService.signIn(userAuthModel)
    }

    override fun sendPasswordResetEmail(email: String): Task<Void> {
        return firebaseService.sendPasswordResetEmail(email)
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseService.getCurrentUser()
    }
}