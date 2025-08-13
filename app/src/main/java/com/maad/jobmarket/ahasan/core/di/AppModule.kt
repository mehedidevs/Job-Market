package com.maad.jobmarket.ahasan.core.di

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.sessions.dagger.Module
import com.google.firebase.sessions.dagger.Provides
import com.maad.jobmarket.ahasan.data.remote.database.FirebaseStudentService
import com.maad.jobmarket.ahasan.domain.repository.StudentRepository
import com.maad.jobmarket.ahasan.domain.usecase.GetStudentUseCase

import jakarta.inject.Singleton


@Module
class FirestoreModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }


    @Provides
    @Singleton
    fun provideStudentRemoteDataSource(firestore: FirebaseFirestore): FirebaseStudentService {
        return provideStudentRemoteDataSource(firestore)
    }

    @Provides
    @Singleton
    fun provideStudentRepository( remoteDataSource: FirebaseStudentService): StudentRepository {
        return provideStudentRepository(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideGetStudentByIdUseCase( repository: StudentRepository): GetStudentUseCase {
        return provideGetStudentByIdUseCase(repository)
    }


}
