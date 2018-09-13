package com.example.kotlinapp.domain

interface Command<T> {
    fun execute():T
}