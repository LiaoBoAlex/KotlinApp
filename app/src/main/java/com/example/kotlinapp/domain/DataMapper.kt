package com.example.kotlinapp.domain

import java.text.DateFormat
import java.util.*
import com.example.kotlinapp.domain.Forecast as ModelForecast
import com.example.kotlinapp.Forecast
import com.example.kotlinapp.ForecastResult

public class ForecastDataMaper{
    private fun convertData(date:Long):String{
        val df= DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return df.format(date*1000)
    }

    private fun convertForcastItemToDomain(forecast: Forecast):ModelForecast{
        return  ModelForecast(convertData(forecast.dt),
                forecast.weather[0].description,
                forecast.temp.max.toInt(),
                forecast.temp.min.toInt(),
                generateIconUrl(forecast.weather[0].icon))
    }

    private fun convertForecastListToDomain(list:List<Forecast>):List<ModelForecast>{
        return list.map { convertForcastItemToDomain(it) }
    }

    fun convertFromDataModel(forecast: ForecastResult):ForecastList{
        return  ForecastList(forecast.city.name, forecast.city.country,
                convertForecastListToDomain(forecast.list))
    }

    private fun generateIconUrl(iconCode:String):String = "http://openweathermap.org/img/w/$iconCode.png"
}