package com.maad.jobmarket.core.di

import com.google.firebase.auth.FirebaseAuth
import com.maad.jobmarket.data.remote.FirebaseService
import com.maad.jobmarket.data.repositoryImpl.AuthRepositoryImpl
import com.maad.jobmarket.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseService(firebaseAuth: FirebaseAuth): FirebaseService {
        return FirebaseService(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseService: FirebaseService): AuthRepository {
        return AuthRepositoryImpl(firebaseService)
    }
}
