package com.example.datashop.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.datashop.domain.NotesViewModel
import com.example.datashop.R
import com.example.datashop.model.Not

@Composable
fun NotesDescription(viewModel: NotesViewModel, navController: NavController){
val SelectedItemData=viewModel.GetdataBYid(viewModel.ID.value).collectAsState(initial = Not(id = 0L,"",""))
    viewModel.Addtitle(SelectedItemData.value.title)
    viewModel.AddDescription(SelectedItemData.value.description)
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "NotesDescriptionScreen", color = Color.White)}, backgroundColor = Color.Green, navigationIcon = { IconButton(
            onClick = {viewModel.update(Not(id = viewModel.ID.value,viewModel.title.value,viewModel.Description.value))
                viewModel.Addtitle("")
                viewModel.AddDescription("")
                navController.navigateUp()}) {
            Icon(painter = painterResource(id =R.drawable.left_arrow), contentDescription =null)
        }})}
    ){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top){
            TextField(value =viewModel.title.value, onValueChange ={viewModel.Addtitle(it)}, modifier = Modifier.fillMaxWidth(), colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White
            ) )
            Divider()
          TextField(value =viewModel.Description.value, onValueChange ={viewModel.AddDescription(it)}, modifier = Modifier.fillMaxWidth(), colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White
          ) )
        }
    }
}