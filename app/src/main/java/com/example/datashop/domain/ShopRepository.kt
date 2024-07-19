package com.example.datashop.domain

import com.example.datashop.model.Data
import com.example.datashop.model.ShopDao
import kotlinx.coroutines.flow.Flow

class ShopRepository( private val sDao: ShopDao){
   suspend fun ADDdata(data: Data){
        sDao.ADD(data)
    }
    suspend fun UpdateData(data: Data){
        sDao.Update(data)
    }
    suspend fun DeleteData(data: Data){
        sDao.Delete(data)
    }
     fun GetDataById(id:Long): Flow<Data> {
       return sDao.GetdatabyID(id)
    }
     fun GetData():Flow<List<Data>>{
       return sDao.Getdata()
    }
}