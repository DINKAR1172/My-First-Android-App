package com.example.datashop.model

import android.content.Context
import androidx.room.Room
import com.example.datashop.Todo.TodoRepository
import com.example.datashop.domain.NotesRepository
import com.example.datashop.domain.ShopRepository

object Graph{
    lateinit var database:Database
    val shoprepository by lazy {
        ShopRepository(sDao = database.shopingdao())
    }
    val NotesRepository by lazy{
        NotesRepository(notesDao = database.Notesdao())
    }
    val Todorepository by lazy {
        TodoRepository(Daoo = database.Tododao())
    }
    fun provide(context:Context){
        database=Room.databaseBuilder(context,Database::class.java,"my-database-name").build()
    }

}