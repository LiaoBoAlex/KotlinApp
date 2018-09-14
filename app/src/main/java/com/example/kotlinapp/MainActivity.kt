package com.example.kotlinapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
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
import java.util.zip.Inflater

class MainActivity : AppCompatActivity(), ToolbarManager {
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val    forecastList:RecyclerView = find(R.id.forecast_list)
//        val forecastList = findViewById<RecyclerView>(R.id.forecast_list)
        forecast_list.layoutManager = LinearLayoutManager(this)

        doAsync {
            val result = RequestForecastCommand("94043").execute()
            uiThread {
                forecast_list.adapter = ForecastListAdapter(
                        result){
                    toast(it.date)
                    Toast.makeText(App.instance, it.date, Toast.LENGTH_SHORT).show()
                }
                toolbarTitle = "${result.city} (${result.country})"
            }
        }
        var zipCode by LongPreferences(App.instance, "ZIP_CODE", 0)
        toast(zipCode.toString())
        attachToScroll(forecast_list)
        initToolbar()


        enableHomeAsUp { finish() }
        val customer = Person()
        customer.sirName = "aaa"
//        Toast.makeText(this, customer.sirName, Toast.LENGTH_SHORT).show()
//        toast(customer.sirName)
//        toast(customer.sirName, Toast.LENGTH_LONG)
        Log.e("liaobo", customer.sirName)

        fun supportsLollipop(code:(Int, String)->Unit){
            code(5, "aaa")
        }

        supportsLollipop { i, s -> run{
            //            toast(s)
            val a = i
        } }

        val a:Int? = null
        val myString = a?.toString()?:"aaa"
        val list = listOf("1","2","3","4","5")
        val array = arrayOf(1, 1, 3, 1, 2, 1, 3, 3, 3, 3)
        Log.e("liaobo", "result = ${sockMerchant(10, array)}")

//        val i = jumpingOnClouds(listOf(0, 0, 0, 1, 0, 1, 0))
        toast(repeatedString("ababa", 3).toString())

    }

    fun countingValleys(n: Int, s: String): Int {
        var v = 1
        var count = 0
        var below = false
        s.forEach {
            if(it.equals('U')){v++}
            if(it.equals('D')){v--}
            if(!below && v<0) below = true
            if(below && v==0) count++
        }
        return count

    }

    fun jumpingOnClouds(c: List<Int>): Int {
        var i = 0
        var count = 0
        while (i<c.size) {
            when (i) {
                c.size - 1 -> i++
                c.size - 2 -> {
                    i++
                    count++
                }
                in 0..c.size - 2 -> {
                    i += if (c[i + 2] == 0) 2 else 1
                    count++
                }
            }
        }
        return count
    }

    fun repeatedString(s: String, n: Long): Long {
        var count = 0L
        val countInN = s.count { it == 'a' }.toLong()
        count = if(n<=s.length){
            s.substring(0 until n.toInt()).count{it =='a'}.toLong()
        }else{
            val d = n/s.length
            val v = n%s.length
            d*countInN + (s.substring(0 until v.toInt()).count{it =='a'}.toLong())
        }
        return count
    }

    fun sockMerchant(n: Int, ar: Array<Int>): Int {
        val map:HashSet<Int> = hashSetOf()
        var pair = 0
        for(it in ar){
            if(map.contains(it)){
                map.remove(it)
                pair++
            }else{
                map.add(it)
            }
        }
        return pair
    }

//    fun Context.toast(message:String, duration:Int=Toast.LENGTH_SHORT){
//        Toast.makeText(this, message, duration).show()
//    }
//
//    private fun find(id: Int): Any {
//        return findViewById(id)
//    }


    class ForecastListAdapter(private val forecastList: ForecastList, private val itemClick: (Forecast)->Unit) :
            RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view:View = LayoutInflater.from(parent.ctx).inflate(R.layout.item_forecast, parent,false)
            return ViewHolder(view, itemClick)
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(forecastList[position])
        }

        override fun getItemCount(): Int = forecastList.size()

        class ViewHolder(val view: View, private val itemClick:(Forecast) -> Unit) : RecyclerView.ViewHolder(view){
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
                    itemView.setOnClickListener {itemClick(this)}
                }
            }
        }

//        interface OnItemClickListener{
//            operator fun invoke(forecast: Forecast)
//        }

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
