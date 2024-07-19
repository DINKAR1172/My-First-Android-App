package com.example.datashop.domain

import androidx.annotation.DrawableRes
import androidx.compose.runtime.mutableStateOf
import com.example.datashop.R

sealed class ScreenR(val route:String){
    object first: ScreenR("first")
    object NotesDescription: ScreenR("NotesDescription")
}
sealed class Screen(val title:String,val Route:String){
    sealed class DrawerItem(val dtitle:String,val dRoute:String,@DrawableRes val Icon:Int):
        Screen(dtitle,dRoute){
    object ShopingList: DrawerItem("Shopping List","ShopingList", R.drawable.shoplll)
        object ToDoList: DrawerItem("ToDo List","ToDoList", R.drawable.to_do_list)
        object Notes: DrawerItem("Notes","Notes", R.drawable.notes)
        object WeatherApp: DrawerItem("Weather","WeatherApp", R.drawable.weather_forecast)

    }
}
val DrawerItem= mutableStateOf(listOf(
    Screen.DrawerItem.ShopingList,
    Screen.DrawerItem.ToDoList,
    Screen.DrawerItem.Notes,
    Screen.DrawerItem.WeatherApp
))