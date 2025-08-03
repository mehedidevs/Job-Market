package com.maad.jobmarket.domain.use_case.auth_usecase

import com.maad.jobmarket.domain.repository.AuthRepository
import com.google.firebase.auth.AuthResult
import com.maad.jobmarket.core.utils.Resource
import com.maad.jobmarket.domain.model.UserAuthModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(userAuthModel: UserAuthModel): Flow<Resource<AuthResult>> = flow {
        try {
            emit(Resource.Loading())
            val result = authRepository.signIn(userAuthModel)
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
