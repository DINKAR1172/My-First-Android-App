package com.example.datashop.Todo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.datashop.model.ToDo
import kotlinx.coroutines.flow.Flow

@Dao
 abstract class TodoDao {
     @Insert(onConflict = OnConflictStrategy.REPLACE)
     abstract suspend fun Add (data:ToDo)
     @Delete
     abstract suspend fun Delete(data:ToDo)
     @Update
     abstract suspend fun Update(data:ToDo)
     @Query("Select * From 'Todo_table'")
     abstract fun GetAllData():Flow<List<ToDo>>
     @Query("Select * From 'Todo_table'where id=:ID")
     abstract fun GetDataBYID(ID:Long):Flow<ToDo>
}