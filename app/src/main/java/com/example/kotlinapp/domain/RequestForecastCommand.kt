package com.example.kotlinapp.domain

import com.example.kotlinapp.Request

class RequestForecastCommand(val zipCode:String):Command<ForecastList>{
    override fun execute(): ForecastList {
        return ForecastDataMaper().convertFromDataModel(Request(zipCode).execute())
    }

}