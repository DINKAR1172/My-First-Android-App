package com.example.datashop.Todo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datashop.model.Data
import com.example.datashop.model.Graph
import com.example.datashop.model.ToDo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Flow

class TodoViewModel(private val repository: TodoRepository=Graph.Todorepository):ViewModel() {

    private val _TodoID= mutableStateOf(0L)
    val TodoID:State<Long> =_TodoID
    fun addID(data:Long){
        _TodoID.value=data
    }
    private val _task= mutableStateOf("")
    val task:State<String> =_task
    fun addTask(task:String){
        _task.value=task
    }
    private val _SDay= mutableStateOf("")
    val SDay:State<String> =_SDay
    fun addSDay(Day:String){
        _SDay.value=Day
    }
    private val _SMonth= mutableStateOf("")
    val SMonth:State<String> =_SMonth
    fun addSMonth(Month:String){
        _SMonth.value=Month
    }
    private val _SYear= mutableStateOf("")
    val SYear:State<String> =_SYear
    fun addSYear(Year:String){
        _SYear.value=Year
    }
    private val _EDay= mutableStateOf("")
    val EDay:State<String> =_EDay
    fun addEDay(Day:String){
        _EDay.value=Day
    }
    private val _EMonth= mutableStateOf("")
    val EMonth:State<String> =_EMonth
    fun addEMonth(Month:String){
        _EMonth.value=Month
    }
    private val _EYear= mutableStateOf("")
    val EYear:State<String> =_EYear
    fun addEYear(Year:String){
        _EYear.value=Year
    }

    lateinit var getdata: kotlinx.coroutines.flow.Flow<List<ToDo>>
   init {
       viewModelScope.launch {
           getdata=repository.getAllData()
       }
   }
    fun Insert(data:ToDo){
        viewModelScope.launch(Dispatchers.IO){
            repository.Insert(data)
        }
    }
    fun Delete(data:ToDo){
        viewModelScope.launch(Dispatchers.IO){
            repository.Delete(data)
        }
    }
    fun Update(data:ToDo){
        viewModelScope.launch(Dispatchers.IO){
            repository.Update(data)
        }
    }
    fun GetDataBYID(ID:Long):kotlinx.coroutines.flow.Flow<ToDo>{
        return repository.GetDataById(ID)
    }

}