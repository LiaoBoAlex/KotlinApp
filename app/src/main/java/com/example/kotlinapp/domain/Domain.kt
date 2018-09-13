package com.example.kotlinapp.domain

public data class ForecastList(val city:String, val country:String, val dailyForecat:List<Forecast>){
    operator fun get(position:Int) = dailyForecat[position]
    fun size() = dailyForecat.size
}
public data class Forecast(val date:String, val description:String, val high:Int, val low:Int, val iconUrl:String)