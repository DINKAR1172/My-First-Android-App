package com.example.datashop.screen
import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.datashop.domain.DrawerItem
import com.example.datashop.domain.NotesViewModel
import com.example.datashop.R
import com.example.datashop.domain.Screen
import com.example.datashop.domain.ScreenR
import com.example.datashop.model.Not
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Notes(navigation:NavController, notesViewModel: NotesViewModel , context: Context) {
    var Alert = remember { mutableStateOf(false) }
    val ScaffoldState:ScaffoldState= rememberScaffoldState()
    val Scope: CoroutineScope = rememberCoroutineScope()
    Scaffold(scaffoldState = ScaffoldState,topBar = {
        TopAppBar(title = { Text(text = "Notes",
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold,
                style =MaterialTheme.typography.h5)}, backgroundColor= colorResource(id = R.color.purple_700), navigationIcon = { IconButton(
            onClick = {Scope.launch { ScaffoldState.drawerState.open() }}) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription =null, tint = Color.White)

        }})

    }, drawerContent={
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(18.dp)){
            items(DrawerItem.value){ item ->
                DrawerDisplay(item = item) {
                    Scope.launch {
                        ScaffoldState.drawerState.close()
                    }
                    navigation.navigate(item.Route)
                }
            }

        }}, drawerBackgroundColor = Color.Black, floatingActionButton = {
        FloatingActionButton(onClick = {Alert.value=true}) {
            Icon(imageVector =Icons.Default.Add, contentDescription =null)
    }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if(Alert.value){com.example.datashop.screen.Alert(Alert =Alert, viewModel =notesViewModel,context)}
            val notess=notesViewModel.NotesData.collectAsState(initial =listOf())
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(notess.value){item ->

                    LazyDisplay(onclick = { notesViewModel.AddIDD(item.id)
                                          navigation.navigate(ScreenR.NotesDescription.route)},data =item, navController = navigation, viewModel = notesViewModel)
                }
            }


        }
    }
}

@Composable
fun LazyDisplay(onclick:()->Unit,data:Not,navController: NavController,viewModel: NotesViewModel){

    Card(backgroundColor = Color.Green,elevation =4.dp, modifier = Modifier
        .padding(8.dp)
        .size(100.dp)
        .clickable {
            onclick()

        }
        .border(BorderStroke(3.dp, Color.Black))){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top){
            Row(modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                    Text(text = data.title,color= Color.Black)

            }
            Divider(thickness = 3.dp, color = Color.Black)
            Text(text = data.description, color = Color.Black, maxLines = 2)
            Divider(thickness = 1.dp, color = Color.Black)
            Row(modifier= Modifier
                .fillMaxWidth()
                .padding(8.dp), horizontalArrangement = Arrangement.Center){
                IconButton(onClick = {viewModel.Delete(data)}) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription =null, tint = Color.Black)
                }
            }
            }
        }
    }


@Composable
fun Alert(Alert:MutableState<Boolean>, viewModel: NotesViewModel, context: Context){
    if(Alert.value) {
        AlertDialog(onDismissRequest = {Alert.value=false}, confirmButton = {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp), horizontalArrangement = Arrangement.End){
            Button(onClick = {Alert.value=false
                             viewModel.Addtitle("")
                             viewModel.AddDescription("")}, colors =ButtonDefaults.buttonColors(Color.Red)) {
                Text(text = "Cancel", color =Color.White)
            }
            Spacer(modifier =Modifier.width(10.dp))
            Button(onClick = {
                if (viewModel.title.value.isEmpty()) {
                    Toast.makeText(context, "Enter Title", Toast.LENGTH_LONG).show()
                } else {
                    viewModel.Insert(
                        Not(
                            title = viewModel.title.value,
                            description = viewModel.Description.value
                        )
                    )
                    Alert.value = false
                }
            },colors =ButtonDefaults.buttonColors(Color.Green)) {
                Text(text = "Done", color =Color.White)
            }
        }
        viewModel.Addtitle("")
        viewModel.AddDescription("")
        },
            title = { Text(text ="Add Dialog")},
            text = {
                Column(modifier=Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                    OutlinedTextField(value =viewModel.title.value, onValueChange ={ viewModel.Addtitle(it)}, label ={ Text(
                        text = "Title"
                    )}, singleLine = true)
                    Text(text = "value of ID is = ${viewModel.ID.value}")
                }


            })
    }
    }
@Composable
fun provideviewModel(content:@Composable ()->Unit){
    val context= LocalContext.current
    val viewModel:NotesViewModel= viewModel(factory =ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application) )
content()
}