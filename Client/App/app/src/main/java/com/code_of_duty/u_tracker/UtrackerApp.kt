package com.code_of_duty.u_tracker

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class UtrackerApp: Application(){

    fun getAppContext(): Context {
        return applicationContext // Obtén el contexto de la aplicación desde la clase de aplicación
    }
}