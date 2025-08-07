package com.maad.jobmarket.domain.use_case.auth_usecase

import com.maad.jobmarket.domain.repository.AuthRepository
import com.google.firebase.auth.AuthResult
import com.maad.jobmarket.core.utils.Resource
import com.maad.jobmarket.domain.model.UserAuthModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(userAuthModel: UserAuthModel): Flow<Resource<AuthResult>> = flow {
        try {
            emit(Resource.Loading())

            val result = signInWithFirebase(userAuthModel)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    private suspend fun signInWithFirebase(userAuthModel: UserAuthModel): AuthResult {
        return suspendCancellableCoroutine { continuation ->
            authRepository.signIn(userAuthModel)
                .addOnSuccessListener { authResult ->
                    // Resume the coroutine with the Firebase result
                    continuation.resume(authResult)
                }
                .addOnFailureListener { exception ->
                    // Resume the coroutine with an exception if sign-in fails
                    continuation.resumeWithException(exception)
                }
        }
    }
}
