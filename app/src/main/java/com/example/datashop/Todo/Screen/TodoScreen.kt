package com.example.datashop.Todo.Screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.datashop.R
import com.example.datashop.Todo.TodoViewModel
import com.example.datashop.domain.DrawerItem
import com.example.datashop.model.ToDo
import com.example.datashop.screen.DrawerDisplay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun TodoScreen(viewModel:TodoViewModel,navController: NavController,context: Context){
    var item= remember { mutableStateOf(ToDo()) }
    val Alert = remember { mutableStateOf(false) }
    val EAlert = remember { mutableStateOf(false) }
    val ScaffoldState:ScaffoldState= rememberScaffoldState()
    val Scope= rememberCoroutineScope()
    Scaffold(scaffoldState = ScaffoldState, drawerBackgroundColor = Color.Black,
        topBar = { TopAppBar(title = { Text(text ="TODO List", style = androidx.compose.material3.MaterialTheme.typography.headlineLarge, color = Color.Black, fontWeight = FontWeight.ExtraBold)},
            backgroundColor = colorResource(id =R.color.purple_700),
            navigationIcon = {
                IconButton(onClick = {
                    Scope.launch {
                        ScaffoldState.drawerState.open()
                    }
                }) {
                  Icon(painter = painterResource(id = R.drawable.baseline_menu_24), contentDescription =null, tint = Color.White)
                }
            })}
   , drawerContent = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp)
            ) {
                items(DrawerItem.value) { item ->
                    DrawerDisplay(item = item) {
                        Scope.launch {
                            ScaffoldState.drawerState.close()
                        }
                        navController.navigate(item.Route)
                    }
                }

            }
        }, floatingActionButton ={ FloatingActionButton(onClick = {Alert.value=true}) {
            Icon(imageVector = Icons.Default.Add, contentDescription =null)
        }}){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)){
            if(Alert.value==true){
                var data= remember { mutableStateOf(ToDo()) }
                viewModel.addTask("")
                viewModel.addSDay("")
                viewModel.addSMonth("")
                viewModel.addSYear("")
                viewModel.addEDay("")
                viewModel.addEMonth("")
                viewModel.addEYear("")
                AlertDialog(viewModel =viewModel, Alert =Alert, data = data.value, context = context)
            }
            if(EAlert.value==true){
                AlertDialog(data =item.value, viewModel =viewModel, Alert =EAlert, context = context)
            }
            var TodoItems =viewModel.getdata.collectAsState(initial = listOf())
            LazyColumn(){
                items(TodoItems.value){Dataa->
                   DisplayItem(
                       data = Dataa,
                       viewModel =viewModel,
                       OnEditClicked = {EAlert.value=true
                       item.value=Dataa
                       }) {
                      viewModel.Delete(ToDo(id =Dataa.id))
                   }
                }
            }

        }
    }
}
@Composable
fun DisplayItem(data:ToDo,viewModel: TodoViewModel,OnEditClicked:()->Unit,ondeleteClick:()->Unit){
    Card(Modifier.padding(16.dp), elevation = 4.dp, backgroundColor =Color.Green, border = BorderStroke(2.dp, Color.Black)){
        var isChecked by remember { mutableStateOf(false) }
        Row(modifier =Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = data.task, color = Color.Black)
            Spacer(modifier = Modifier.width(2.dp))
            if(isChecked==true){
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                    Text(text = "Completed", color = Color.White)
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(painter = painterResource(id = R.drawable.baseline_done_all_24), contentDescription =null, tint = Color.White)
                }
            }
            else{
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween){
                    Text(text = "${data.Sdate}/${data.SMonth}/${data.SYear}", color = Color.Black)
                    Icon(painter = painterResource(id =R.drawable.baseline_arrow_downward_24), contentDescription =null, tint = Color.Black)
                    Text(text ="${data.Edate}/${data.EMonth}/${data.EYear}", color = Color.Black)
                }
                IconButton(onClick = {OnEditClicked()}) {
                    Icon(imageVector = Icons.Default.Create, contentDescription =null)
                }
            }
         IconButton(onClick = {ondeleteClick()}){
             Icon(painter = painterResource(id =R.drawable.delete), contentDescription =null, tint = Color.Black)
         }

            Checkbox(checked =isChecked, onCheckedChange ={isChecked=it})
        }

    }

}
@Composable
fun AlertDialog(data: ToDo,viewModel: TodoViewModel,Alert:MutableState<Boolean>,context: Context){
    if(data.id!=0L){
        val clickedData=viewModel.GetDataBYID(data.id).collectAsState(initial = ToDo(0L,"",0,0,0,0,0,0))
        viewModel.addTask(clickedData.value.task)
        viewModel.addID(clickedData.value.id)
        viewModel.addSDay(clickedData.value.Sdate.toString())
        viewModel.addSMonth(clickedData.value.SMonth.toString())
        viewModel.addSYear(clickedData.value.SYear.toString())
        viewModel.addEDay(clickedData.value.Edate.toString())
        viewModel.addEMonth(clickedData.value.EMonth.toString())
        viewModel.addEYear(clickedData.value.EYear.toString())
    }
    androidx.compose.material.AlertDialog(onDismissRequest = {Alert.value=false}, confirmButton = {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp), horizontalArrangement =Arrangement.End){
        Button(onClick = { Alert.value=false
                         }, colors = ButtonDefaults.buttonColors(Color.Red)) {
            Text(text = "Cancel", color = Color.White)
        }
        Spacer(modifier = Modifier.width(2.dp))
        Button(onClick = {
            if(viewModel.task.value.isNotEmpty()&&viewModel.SDay.value.isNotEmpty()&&viewModel.SMonth.value.isNotEmpty()&&viewModel.SYear.value.isNotEmpty()
                &&viewModel.EDay.value.isNotEmpty()&&viewModel.EMonth.value.isNotEmpty()&&viewModel.EYear.value.isNotEmpty()){
             if(data.id!=0L){
                 viewModel.Update(ToDo(viewModel.TodoID.value,viewModel.task.value,viewModel.SDay.value.toInt(),viewModel.SMonth.value.toInt(),viewModel.SYear.value.toInt(),viewModel.EDay.value.toInt(),viewModel.EMonth.value.toInt(),viewModel.EYear.value.toInt()))
                 Alert.value=false
             }
                else{
                 viewModel.Insert(ToDo(viewModel.TodoID.value,viewModel.task.value,viewModel.SDay.value.toInt(),viewModel.SMonth.value.toInt(),viewModel.SYear.value.toInt(),viewModel.EDay.value.toInt(),viewModel.EMonth.value.toInt(),viewModel.EYear.value.toInt()))
                 Alert.value=false
                }
            }
            else{
                Toast.makeText(context,"Enter the blank Spaces",Toast.LENGTH_LONG).show()
            }
        }, colors = ButtonDefaults.buttonColors(Color.Green)) {
            Text(text = "Confirm", color = Color.White)
        }

    }
}, title = { Text(text ="Add Task")},
    text = {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
            OutlinedTextField(value =viewModel.task.value, onValueChange ={viewModel.addTask(it)})
            Text(text = "StartingDate", color = Color.Black, style = MaterialTheme.typography.h5)
            Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween){
                OutlinedTextField(value =viewModel.SDay.value, onValueChange ={viewModel.addSDay(it)}, label ={ Text(text = "Day")}, modifier = Modifier.size(width = 60.dp, height =60.dp))
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(value =viewModel.SMonth.value, onValueChange ={viewModel.addSMonth(it)}, modifier = Modifier.size(width = 80.dp, height =60.dp), label ={ Text(text = "Month")})
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(value =viewModel.SYear.value, onValueChange ={viewModel.addSYear(it)}, modifier = Modifier.size(width = 80.dp, height =60.dp), label ={ Text(text = "Year")})

            }
            Text(text = "EndingDate", color = Color.Black, style = MaterialTheme.typography.h5)
            Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween){
                OutlinedTextField(value =viewModel.EDay.value, onValueChange ={viewModel.addEDay(it)}, label ={ Text(text = "Day")}, modifier = Modifier.size(width = 60.dp, height =60.dp))
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(value =viewModel.EMonth.value, onValueChange ={viewModel.addEMonth(it)}, modifier = Modifier.size(width = 80.dp, height =60.dp), label ={ Text(text = "Month")})
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(value=viewModel.EYear.value, onValueChange ={viewModel.addEYear(it)}, modifier = Modifier.size(width = 80.dp, height =60.dp), label ={ Text(text = "Year")})

            }
        }
    })
}