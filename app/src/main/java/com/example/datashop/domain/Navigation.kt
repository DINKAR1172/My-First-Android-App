package com.example.datashop.domain

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.datashop.Todo.Screen.TodoScreen
import com.example.datashop.Todo.TodoViewModel
import com.example.datashop.Weather.WeatherScreen
import com.example.datashop.Weather.WeatherviewModel
import com.example.datashop.screen.FirstScreen
import com.example.datashop.screen.Notes
import com.example.datashop.screen.NotesDescription
import com.example.datashop.screen.Shopping

@Composable
fun Navigation(navHostController: NavHostController= rememberNavController()) {
    val WeatherviewModel:WeatherviewModel= viewModel()
    val viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val NotesViewModel:NotesViewModel= androidx.lifecycle.viewmodel.compose.viewModel()
    val TodoViewModel:TodoViewModel= androidx.lifecycle.viewmodel.compose.viewModel()
    val context = LocalContext.current
    NavHost(navController = navHostController, startDestination = ScreenR.first.route) {
        composable(ScreenR.first.route) {
            FirstScreen(navHostController)
        }
composable(Screen.DrawerItem.ShopingList.Route){
    Shopping(viewModel = viewModel, context =context,navHostController)
}
        composable(Screen.DrawerItem.Notes.Route){
            Notes(navHostController, context = context, notesViewModel = NotesViewModel)
        }
        composable(ScreenR.NotesDescription.route){
           NotesDescription(navController = navHostController, viewModel = NotesViewModel)
        }
        composable(Screen.DrawerItem.ToDoList.Route){
            TodoScreen(viewModel =TodoViewModel, navController =navHostController,context)
        }
        composable(Screen.DrawerItem.WeatherApp.Route){
            WeatherScreen(WeatherviewModel,navHostController,context)
        }
    }
}