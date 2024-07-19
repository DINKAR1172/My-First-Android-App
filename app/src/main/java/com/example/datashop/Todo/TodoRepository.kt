package com.example.datashop.Todo

import androidx.room.Insert
import com.example.datashop.model.ToDo
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val Daoo:TodoDao) {
    suspend fun Insert(data:ToDo){
        Daoo.Add(data)
    }
    suspend fun Delete(data: ToDo){
        Daoo.Delete(data)
    }
    suspend fun Update(data: ToDo){
        Daoo.Update(data)
    }
    fun getAllData():Flow<List<ToDo>>{
        return Daoo.GetAllData()
    }
    fun GetDataById(Id:Long):Flow<ToDo>{
       return Daoo.GetDataBYID(Id)
    }

}