package com.maad.jobmarket.domain.use_case.auth_usecase

import com.maad.jobmarket.core.utils.Resource
import com.maad.jobmarket.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class ResetPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(email: String): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())

            // Call the reset password function and handle asynchronously using suspendCancellableCoroutine
            val result = resetPasswordWithFirebase(email)
            emit(Resource.Success(result))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    private suspend fun resetPasswordWithFirebase(email: String): Boolean {
        return suspendCancellableCoroutine { continuation ->
            authRepository.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        println("Password reset email sent")
                        continuation.resume(true) // Return success
                    } else {
                        println("Password reset failed")
                        continuation.resume(false) // Return failure
                    }
                }
        }
    }
}