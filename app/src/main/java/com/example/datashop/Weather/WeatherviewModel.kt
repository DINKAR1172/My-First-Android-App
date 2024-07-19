package com.example.datashop.Weather

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datashop.Weather.Api.Constant
import com.example.datashop.Weather.Api.WeatherModel
import com.example.datashop.Weather.Api.WeatherService
import kotlinx.coroutines.launch

class WeatherviewModel:ViewModel() {
     private var _city= mutableStateOf("")
    var city:State<String> =_city
    fun setCity(data:String){
        _city.value=data
    }
     fun Fetchdata(data:String){
         _WeatherContent.value=_WeatherContent.value.copy(lodaing = true)
        viewModelScope.launch {
            try {
                val response= WeatherService.getWeather(Constant.apiKey,data)
                _WeatherContent.value=_WeatherContent.value.copy(lodaing = false,
                    data=response,error = null)
            }
            catch (e:Exception){
                _WeatherContent.value=_WeatherContent.value.copy(lodaing = false,
                    error ="Error Fetching data ${e.message}")
            }
        }
    }
    private var _WeatherContent= mutableStateOf(Weatherdata())
    var WeatherContent:State<Weatherdata> =_WeatherContent
    data class Weatherdata(var lodaing:Boolean=false,var data:WeatherModel?=null,var error:String?=null)

}