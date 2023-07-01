package com.code_of_duty.u_tracker.di

import com.code_of_duty.u_tracker.data.network.UtrackerApiClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /*para test emulador*/
    private const val EMULATOR_URL = "http://10.0.2.2:8080/api/"
    //private const val BASE_URL = "https://10.0.20.40:8085/api/"
  
    //REAL API
    private const val BASE_URL = "https://utracker.me/api/"
    //TESTING API
    private const val TESTING_URL = "http://192.168.1.2:8080/api/"

    @Provides
    @Singleton
    fun providesUtracker(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(TESTING_URL)
        .build()

    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): UtrackerApiClient = retrofit.create(UtrackerApiClient::class.java)

    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()
}