package com.code_of_duty.u_tracker.di

import com.code_of_duty.u_tracker.ui.screens.login.LoginViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    fun providesLoginViewModel(): LoginViewModel {
        return LoginViewModel()
    }

}