package com.example.thebookofbooks

import android.app.Application
import com.example.thebookofbooks.data.AppContainer
import com.example.thebookofbooks.data.DefaultAppContainer

class BooksApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }

}