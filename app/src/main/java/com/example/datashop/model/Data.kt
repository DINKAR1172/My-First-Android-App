package com.example.datashop.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey

@Entity(tableName ="Shopping_table")
data class Data(
    @PrimaryKey(autoGenerate = true)
    val  id:Long=0L,
    @ColumnInfo("NAME")
    val Name:String="",
    @ColumnInfo("Quantity")
    val Qty:Int=0)

@Entity(tableName ="Notes_table")
data class Not(
    @PrimaryKey(autoGenerate = true)
    val id: Long=0L,
    @ColumnInfo(name ="title")
    val title:String="",
    @ColumnInfo(name ="descrription")
    val description:String=""
)
@Entity(tableName = "Todo_table")
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id:Long=0L,
    @ColumnInfo(name = "task")
    val task:String="",
    @ColumnInfo("StaringDate")
    val Sdate:Int=0,
    @ColumnInfo("StaringMonth")
    val SMonth:Int=0,
    @ColumnInfo("StaringYear")
    val SYear:Int=0,
    @ColumnInfo("EndingDate")
    val Edate:Int=0,
    @ColumnInfo("EndingMonth")
    val EMonth:Int=0,
    @ColumnInfo("EndingYear")
    val EYear:Int=0,
    )