package com.outdoors.sliide.di

import com.outdoors.sliide.repository.BaseUsersRepository
import com.outdoors.sliide.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRepository(repo: UsersRepository): BaseUsersRepository
}