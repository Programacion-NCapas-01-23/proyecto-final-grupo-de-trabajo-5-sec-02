package com.code_of_duty.u_tracker.di

import android.content.Context
import androidx.room.Room
import com.code_of_duty.u_tracker.data.database.UtrackerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext appContext: Context): UtrackerDatabase =
        Room.databaseBuilder(appContext, UtrackerDatabase::class.java, "utracker.db")
            .build()

    @Provides
    @Singleton
    fun providesUserDao(database: UtrackerDatabase) = database.userDao()

    @Provides
    @Singleton
    fun providesTokenDao(database: UtrackerDatabase) = database.userTokenDao()

    @Provides
    @Singleton
    fun providesCycleDao(database: UtrackerDatabase) = database.cycleDao()

    @Provides
    @Singleton
    fun providesSubjectDao(database: UtrackerDatabase) = database.subjectDao()

    @Provides
    @Singleton
    fun providesPrerequisiteDao(database: UtrackerDatabase) = database.prerequisiteDao()

    @Provides
    @Singleton
    fun providesGradeDao(database: UtrackerDatabase) = database.gradeDao()

    //if you have more DAOs, you can provide them here as well
}