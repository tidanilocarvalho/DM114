package br.com.danilo.projectdm114

import android.app.Application
import android.content.Context

class MainApplication : Application() {

    companion object {
        private var instance: MainApplication? = null

        fun getApplicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    init {
        instance = this
    }

}