package com.maad.jobmarket.domain.use_case.auth_usecase

import com.google.firebase.auth.AuthResult
import com.maad.jobmarket.core.utils.Resource
import com.maad.jobmarket.domain.model.UserAuthModel
import com.maad.jobmarket.domain.repository.AuthRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(userAuthModel: UserAuthModel): Flow<Resource<AuthResult>> = flow {
        try {
            emit(Resource.Loading())
            val result = authRepository.signUp(userAuthModel)
            if (result.isSuccessful) {
                emit(Resource.Success(result.result!!))
            } else {
                emit(Resource.Error(result.exception?.localizedMessage ?: "An unexpected error occurred"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}
