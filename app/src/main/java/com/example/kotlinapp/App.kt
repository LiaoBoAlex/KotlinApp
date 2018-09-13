package com.example.kotlinapp

import android.app.Application

public class App:Application(){
//test git
    companion object {
        lateinit var instance:App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this@App
    }
}