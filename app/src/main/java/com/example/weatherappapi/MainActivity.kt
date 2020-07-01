package com.example.weatherappapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.weatherappapi.model.ApiWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    companion object{
        const val BASE_URL = "https://api.openweathermap.org/"
        const val API_KEY = "68587bbcf689b8a150f59cc139af9143"
    }

    private lateinit var edtCity: EditText
    private lateinit var edtCountry: EditText
    private lateinit var txtCityvalue: TextView
    private lateinit var txtWeatherValue: TextView
    private lateinit var txtTemperatureValue: TextView
    private lateinit var txtThermalSensationValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.edtCity = findViewById(R.id.edt_city)
        this.edtCountry = findViewById(R.id.edt_country)
        this.txtCityvalue = findViewById(R.id.txt_city_value)
        this.txtWeatherValue = findViewById(R.id.txt_weather_value)
        this.txtTemperatureValue = findViewById(R.id.txt_temperature_value)
        this.txtThermalSensationValue = findViewById(R.id.txt_thermal_sensation_value)

        val button: Button = findViewById(R.id.btn_search)
        button.setOnClickListener {
            getWeather()
        }
    }

    private fun getWeather(){
        val sendCity = edtCity.text.toString()
        val sendCountry = edtCountry.text.toString()

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiWeatherInterface::class.java)
        val call = service.getCurrentWeatherByCity("$sendCity, $sendCountry","metric",API_KEY)

        call.enqueue(object : Callback<ApiWeather>{
            override fun onFailure(call: Call<ApiWeather>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ApiWeather>, response: Response<ApiWeather>) {
                if (response.code() == 200){
                    val responseWeather = response.body()!!
                    txtCityvalue.text = responseWeather.name
                    txtWeatherValue.text = "${responseWeather.weather[0].main}"
                    txtTemperatureValue.text = "${responseWeather.main.temp}C"
                    txtThermalSensationValue.text = "${responseWeather.main.feelsLike}C"
                }
            }
        })
    }
}
