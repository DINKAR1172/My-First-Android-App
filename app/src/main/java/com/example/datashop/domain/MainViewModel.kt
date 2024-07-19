package com.example.datashop.domain

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datashop.model.Data
import com.example.datashop.model.Graph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ShopRepository =Graph.shoprepository):ViewModel(){

    private var _name = mutableStateOf("")
    var name:State<String> =_name
     private var _Qty = mutableStateOf("")
    var Qty:State<String> =_Qty
     private var _Id = mutableStateOf<Long>(0L)
    var Id:State<Long> =_Id
    fun addname(title:String){
        _name.value=title
    }
    fun addQty(Quantity:String){
        _Qty.value=Quantity
    }
    fun addId(id:Long){
        _Id.value=id
    }



    lateinit var getdata:Flow<List<Data>>
    init {
        viewModelScope.launch {
          getdata=repository.GetData()
        }
    }
    fun Insert(data: Data){
        viewModelScope.launch (Dispatchers.IO){
            repository.ADDdata(data)
        }
    }
    fun delete(data: Data){
        viewModelScope.launch(Dispatchers.IO) {
            repository.DeleteData(data)
        }
    }
    fun Update(data: Data){
        viewModelScope.launch (Dispatchers.IO){
            repository.UpdateData(data)
        }
    }
    fun getdatabyID(ID:Long):Flow<Data>{
            return repository.GetDataById(id=ID)
    }

}