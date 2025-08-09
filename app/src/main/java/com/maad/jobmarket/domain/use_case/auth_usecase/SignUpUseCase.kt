package com.maad.jobmarket.domain.use_case.auth_usecase

import com.google.firebase.auth.AuthResult
import com.maad.jobmarket.core.utils.Resource
import com.maad.jobmarket.domain.model.UserAuthModel
import com.maad.jobmarket.domain.repository.AuthRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(userAuthModel: UserAuthModel): Flow<Resource<AuthResult>> = flow {
        try {
            emit(Resource.Loading())

            val result = signUpWithFirebase(userAuthModel)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    private suspend fun signUpWithFirebase(userAuthModel: UserAuthModel): AuthResult {
        return suspendCancellableCoroutine { continuation ->
            authRepository.signUp(userAuthModel)
                .addOnSuccessListener { authResult ->
                    continuation.resume(authResult)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }
}
