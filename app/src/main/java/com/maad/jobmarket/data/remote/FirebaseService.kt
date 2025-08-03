package com.maad.jobmarket.data.remote

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.maad.jobmarket.domain.model.UserAuthModel
import jakarta.inject.Inject

class FirebaseService @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    fun signUp(userAuthModel: UserAuthModel): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(userAuthModel.email, userAuthModel.password)
    }

    fun signIn(userAuthModel: UserAuthModel): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(userAuthModel.email, userAuthModel.password)
    }

    fun sendPasswordResetEmail(email: String): Task<Void> {
        return firebaseAuth.sendPasswordResetEmail(email)
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

}
