package com.example.kotlinapp

import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class LongPreferences(val context:Context, val key:String, val default:Long):ReadWriteProperty<Any?, Long>{

    val prefs by lazy { context.getSharedPreferences("default", Context.MODE_PRIVATE) }

    override fun getValue(thisRef: Any?, property: KProperty<*>): Long {
        return prefs.getLong(key, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) {
        prefs.edit().putLong(key, default).commit()
    }

}