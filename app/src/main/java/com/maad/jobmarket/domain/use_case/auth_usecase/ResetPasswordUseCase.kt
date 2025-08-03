package com.maad.jobmarket.domain.use_case.auth_usecase

import com.maad.jobmarket.core.utils.Resource
import com.maad.jobmarket.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(email: String): Flow<Resource<Void>> = flow {
        try {
            emit(Resource.Loading())
            val result = authRepository.sendPasswordResetEmail(email)
            if (result.isSuccessful) {
                emit(Resource.Success(result.result))
            } else {
                emit(Resource.Error(result.exception?.localizedMessage ?: "An unexpected error occurred"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}