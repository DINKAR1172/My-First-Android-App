package com.example.datashop.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ShopDao(){
    @Insert(onConflict = OnConflictStrategy.IGNORE)
   abstract suspend  fun ADD(data:Data)
    @Update
    abstract  suspend fun Update(data: Data)
    @Delete
    abstract suspend fun Delete(data: Data)
    @Query("Select * from 'Shopping_table'")
    abstract fun Getdata():Flow<List<Data>>
    @Query("Select * from 'Shopping_table' where id=:id")
    abstract fun GetdatabyID(id:Long):Flow<Data>
}
@Dao
abstract class NotesDao(){
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     abstract suspend fun Insert(data:Not)
     @Update
     abstract suspend fun Update(data:Not)
     @Delete
     abstract suspend fun Delete(data: Not)
     @Query("Select * from 'Notes_table'")
     abstract fun GetAlldata():Flow<List<Not>>
     @Query("Select * from 'Notes_table' where id=:Id")
     abstract fun getdatabyId(Id:Long):Flow<Not>
}