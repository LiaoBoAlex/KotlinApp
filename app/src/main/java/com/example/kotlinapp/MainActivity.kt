package com.example.kotlinapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.kotlinapp.domain.Forecast
import com.example.kotlinapp.domain.ForecastList
import com.example.kotlinapp.domain.RequestForecastCommand
import com.example.kotlinapp.extension.ctx
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_forecast.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val    forecastList:RecyclerView = find(R.id.forecast_list)
//        val forecastList = findViewById<RecyclerView>(R.id.forecast_list)
        forecast_list.layoutManager = LinearLayoutManager(this)

        doAsync {
            val result = RequestForecastCommand("94043").execute()
            uiThread { forecast_list.adapter = ForecastListAdapter(
                    result){
                toast(it.date)
            }
            }
        }

        val customer = Person()
        customer.sirName = "aaa"
//        Toast.makeText(this, customer.sirName, Toast.LENGTH_SHORT).show()
        toast(customer.sirName)
//        toast(customer.sirName, Toast.LENGTH_LONG)
        Log.e("liaobo", customer.sirName)

        fun supportsLollipop(code:(Int, String)->Unit){
            code(5, "aaa")
        }

        supportsLollipop { i, s -> run{
            toast(s)
            val a = i
        } }
    }

//    fun Context.toast(message:String, duration:Int=Toast.LENGTH_SHORT){
//        Toast.makeText(this, message, duration).show()
//    }
//
//    private fun find(id: Int): Any {
//        return findViewById(id)
//    }

    class ForecastListAdapter(private val forecastList: ForecastList, private val hehe: (Forecast)->Unit) :
            RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view:View = LayoutInflater.from(parent.ctx).inflate(R.layout.item_forecast, parent,false)
            return ViewHolder(view, hehe)
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(forecastList[position])
        }

        override fun getItemCount(): Int = forecastList.size()

        class ViewHolder(val view: View, private val itemClick:(Forecast)->Unit) : RecyclerView.ViewHolder(view){
//            private val iconView:ImageView = view.find(R.id.icon)
//            private val dateView:TextView = view.find(R.id.date)
//            private val descriptionView:TextView = view.find(R.id.descriptionText)
//            private val maxTemperatureView:TextView = view.find(R.id.maxTemperature)
//            private val minTemperature:TextView = view.find(R.id.minTemperature)

            fun bind(forecast:Forecast){
                with(forecast){
                    Glide.with(itemView.ctx).load(iconUrl).into(itemView.icon)
                    itemView.date.text = date
                    itemView.descriptionText.text = description
                    itemView.maxTemperature.text = high.toString()
                    itemView.minTemperature.text = low.toString()
                    itemView.setOnClickListener ({v ->
                        Log.e("liaobo", "${forecast.date}")
                        Log.e("liaobo", "${forecast.date}")
                        val view = v

                    })
                }
            }
        }

        interface OnItemClickListener{
            operator fun invoke(forecast: Forecast)
        }

    }

    class Person {
        var givenName = ""
            get() = field.toUpperCase()
            set(value)
            {
                field = "Given Name:$value"
            }
        var sirName = ""
            get() = field.toUpperCase()
            set(value)
            {
                field = "Sir Name:$value"
            }
    }
}
