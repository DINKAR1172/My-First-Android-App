package com.example.datashop.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.datashop.domain.DrawerItem
import com.example.datashop.R
import com.example.datashop.domain.Screen
import com.example.datashop.domain.MainViewModel
import com.example.datashop.model.Data
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Shopping(viewModel: MainViewModel, context: Context, navigation:NavController) {
    var SelectedItemData= remember { mutableStateOf(Data()) }
    var Alert = remember { mutableStateOf(false) }
    var EAlert = remember { mutableStateOf(false) }
    val Scope: CoroutineScope = rememberCoroutineScope()
    val ScaffoldState: ScaffoldState = rememberScaffoldState()

    Scaffold(drawerBackgroundColor = Color.Black, scaffoldState = ScaffoldState, topBar = {
        TopAppBar(title = {
            Text(
                text = "Shopping List App",
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold
            )
        }, backgroundColor = colorResource(id = R.color.purple_700),
            navigationIcon = {
                IconButton(onClick = {
                    Scope.launch {
                        ScaffoldState.drawerState.open()
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_menu_24),
                        contentDescription = null
                    )
                }
            })
    },
        floatingActionButton = {
            FloatingActionButton(onClick = { Alert.value = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = null
                )
            }
        }, drawerContent = {
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
                        navigation.navigate(item.Route)
                    }
                }

            }
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            if (Alert.value == true) {
                SelectedItemData.value= Data()
              com.example.datashop.screen.AlertDialog(data =SelectedItemData.value, Alert=Alert, context =context)

            }
            else if(EAlert.value== true) {
                com.example.datashop.screen.AlertDialog(data =SelectedItemData.value, Alert =EAlert, context =context)
            }
                val ShopingList = viewModel.getdata.collectAsState(initial = listOf())

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(ShopingList.value, key = { data -> data.id }) { Dd ->
                        viewModel.addId(Dd.id)
                        val dismissState = rememberDismissState(
                            confirmStateChange = {
                                if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                                    viewModel.delete(Dd)
                                }
                                true
                            }
                        )
                        SwipeToDismiss(state = dismissState,
                            background = {
                                val color by animateColorAsState(
                                    if (dismissState.dismissDirection == DismissDirection.EndToStart)
                                        Color.Red
                                    else
                                        Color.Transparent,
                                    label = ""
                                )
                                val alignment = Alignment.CenterEnd
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color)
                                        .padding(horizontal = 20.dp),
                                    contentAlignment = alignment,
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            },
                            directions = setOf(DismissDirection.EndToStart),
                            dismissThresholds = { FractionalThreshold(0.75f) }

                        ) {
                            Displaydata(data = Dd, viewModel = viewModel) {
                                SelectedItemData.value = Dd
                                EAlert.value = true

                            }
                        }
                    }


                }
            }

        }


        }



@Composable
fun Displaydata(data: Data, viewModel: MainViewModel= androidx.lifecycle.viewmodel.compose.viewModel(), onEditClick:()->Unit){
    Card(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
        .border(width = 3.dp, color = Color.Green, shape = RoundedCornerShape(5.dp)), elevation = 4.dp){
        Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
          Column(verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.Start){
              Text(text = "id value is = ${data.id}")
              Text(text = "Item Name :-${data.Name}")
              Text(text ="Item Quantity :- ${data.Qty}")
          }
            IconButton(onClick = {

                onEditClick()
            }) {
                Icon(imageVector =Icons.Default.Create, contentDescription =null)
            }

        }
    }
}
@Composable
fun DrawerDisplay(selected:Boolean=false,
                  item: Screen.DrawerItem,
                  OnDrawerItemClicked:() ->Unit){
    val background=if(selected)Color.DarkGray else Color.Transparent
Row(modifier = Modifier
    .fillMaxWidth()
    .padding(8.dp)
    .clickable { OnDrawerItemClicked() }
    .background(background)){
    androidx.compose.material.Icon(painter = painterResource(id =item.Icon), contentDescription =null, tint = Color.White)
    Spacer(modifier = Modifier.width(18.dp))
    Text(text =item.dtitle, color = Color.White)
}
}
@Composable
fun AlertDialog(data:Data, viewModel: MainViewModel= androidx.lifecycle.viewmodel.compose.viewModel(), Alert:MutableState<Boolean>, context: Context){
    if(data.id!=0L){
        val ClickedData=viewModel.getdatabyID(data.id).collectAsState(initial = Data(0L,"",0))
            viewModel.addId(ClickedData.value.id)
            viewModel.addname(ClickedData.value.Name)
            viewModel.addQty(ClickedData.value.Qty.toString())
    }
    else{
        viewModel.addname("")
        viewModel.addQty("")
    }
    AlertDialog(onDismissRequest = {Alert.value=false}, confirmButton = {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically){
        Button(onClick = { Alert.value = false }, colors = ButtonDefaults.buttonColors(Color.Red)) {
            Text(text = "Cancel", color = Color.White)
        }
            Spacer(modifier = Modifier.width(3.dp))
            Button(onClick = {
                if(viewModel.name.value.isNotEmpty()&&viewModel.Qty.value.isNotEmpty()){
                    if(data.id==0L){
                        viewModel.Insert(Data(viewModel.Id.value,viewModel.name.value,viewModel.Qty.value.toInt()))
                    }
                    else{
                        viewModel.Update(Data(viewModel.Id.value,viewModel.name.value,viewModel.Qty.value.toInt()))
                    }
                    Alert.value=false
                    viewModel.addname("")
                    viewModel.addQty("")
                    viewModel.addId(0L)
                }
                else {
                    Toast.makeText(context,"Please Enter the data",Toast.LENGTH_LONG).show()
                }

            }, colors = ButtonDefaults.buttonColors(Color.Green)) {
                Text(text = "Confirm", color = Color.White)
            }

    }},
   title = { Text(text = if(data.id!=0L){"Update data"}else
   {"Add data" }, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.ExtraBold)},
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                OutlinedTextField(value =viewModel.name.value, onValueChange ={viewModel.addname(it)}, label ={ Text(
                    text = if (data.id != 0L) {"Update Name"}
                    else {"Add Name"}
                )})
                Spacer(modifier =Modifier.height(4.dp))
                OutlinedTextField(value =viewModel.Qty.value, onValueChange ={viewModel.addQty(it)},label ={ Text(
                    text = if (data.id != 0L) {"Update Quantity"}
                    else {"Add Quantity"}
                )})
            }
        })
}
