package com.himanshu.studybuddy.di

import com.himanshu.studybuddy.data.repository.SessionRepositoryImpl
import com.himanshu.studybuddy.data.repository.SubjectRepositoryImpl
import com.himanshu.studybuddy.data.repository.TaskRepositoryImpl
import com.himanshu.studybuddy.domain.repository.SessionRepository
import com.himanshu.studybuddy.domain.repository.SubjectRepository
import com.himanshu.studybuddy.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindSubjectRepository(
        impl: SubjectRepositoryImpl
    ): SubjectRepository

    @Singleton
    @Binds
    abstract fun bindTaskRepository(
        impl: TaskRepositoryImpl
    ): TaskRepository

    @Singleton
    @Binds
    abstract fun bindSessionRepository(
        impl: SessionRepositoryImpl
    ): SessionRepository
}
