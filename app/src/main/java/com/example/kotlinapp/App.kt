package com.example.kotlinapp

import android.app.Application
import kotlin.properties.Delegates

public class App:Application(){
//test git
    companion object {
        var instance:App by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this@App
    }
}