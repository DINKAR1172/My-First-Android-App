package com.example.datashop.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datashop.model.Graph
import com.example.datashop.model.Not
import com.example.datashop.domain.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NotesViewModel(private val NotesRepository: NotesRepository =Graph.NotesRepository):ViewModel() {
    private var _Color= mutableStateOf<Color>(androidx.compose.ui.graphics.Color.Transparent)
    var Color:State<Color> =_Color
    fun AddColor(data:Color){
        _Color.value=data
    }
     private var _title= mutableStateOf<String>("")
     var title:State<String> =_title
    fun Addtitle(data:String){
        _title.value=data
    }
    private var _Description= mutableStateOf<String>("")
    var Description:State<String> =_Description
    fun AddDescription(data:String){
        _Description.value=data
    }
    private val _ID= mutableStateOf(0L)
    val ID:State<Long>  =_ID
    fun AddIDD(data:Long){
        _ID.value=data
    }
    fun getSelectedItemID():Long?=ID.value

     lateinit var NotesData:Flow<List<Not>>
     init {
         viewModelScope.launch{
             NotesData=NotesRepository.GetAlldata()
         }
     }
    fun Insert(data:Not){
        viewModelScope.launch(Dispatchers.IO){
            NotesRepository.InsertNotes(data)
        }

    }
    fun update(data:Not){
        viewModelScope.launch(Dispatchers.IO){
            NotesRepository.UpdateNotes(data)
        }

    }
    fun Delete(data: Not){
        viewModelScope.launch(Dispatchers.IO){
            NotesRepository.DeleteNotes(data)
        }
    }
    fun GetdataBYid(ID:Long):Flow<Not>{
        return NotesRepository.GetdatabyId(ID)
    }
}