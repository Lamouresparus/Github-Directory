package com.example.greyassessment.di


import com.example.greyassessment.data.GithubRepositoryImpl
import com.example.greyassessment.domain.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun dataRepository(repositoryImpl: GithubRepositoryImpl): GithubRepository
}