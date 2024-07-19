package com.example.datashop.domain

import androidx.room.Update
import com.example.datashop.model.Not
import com.example.datashop.model.NotesDao
import kotlinx.coroutines.flow.Flow

class NotesRepository(private val notesDao: NotesDao) {
    suspend fun InsertNotes(data: Not){
        notesDao.Insert(data)
    }
    suspend fun UpdateNotes(data: Not){
        notesDao.Update(data)
    }
    suspend fun DeleteNotes(data: Not){
        notesDao.Delete(data)
    }
    fun GetAlldata():Flow<List<Not>>{
       return notesDao.GetAlldata()
    }
    fun GetdatabyId(Id:Long):Flow<Not>{
       return notesDao.getdatabyId(Id)
    }

}