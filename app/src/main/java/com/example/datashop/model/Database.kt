package com.example.datashop.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.datashop.Todo.TodoDao

@Database(
    entities = [Data::class,Not::class,ToDo::class],
    version = 1,
    exportSchema = false
)
abstract class Database():RoomDatabase() {
    abstract fun shopingdao():ShopDao
    abstract fun Notesdao():NotesDao
    abstract fun Tododao():TodoDao
}