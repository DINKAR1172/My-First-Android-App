package com.example.datashop.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.datashop.R
import com.example.datashop.domain.Screen

@Composable
fun FirstScreen(navController: NavController){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally){

            Text(text = "Multiple App", style = MaterialTheme.typography.headlineLarge, color = Color.Black, fontWeight = FontWeight.ExtraBold)
            Divider(modifier = Modifier.padding(start = 8.dp, end = 8.dp,), thickness =5.dp, color = Color.Black)
            Text(text ="This single app consist of 4 multiple app")
        Spacer(modifier = Modifier.height(60.dp))
        Displayicon(Icon = R.drawable.llllllllllllll, Name ="Shopping List" )
        Spacer(modifier = Modifier.height(40.dp))
        Displayicon(Icon = R.drawable.checklist, Name ="ToDo List" )
        Spacer(modifier = Modifier.height(40.dp))
        Displayicon(Icon = R.drawable.paper, Name ="Notes" )
        Spacer(modifier = Modifier.height(40.dp))
        Displayicon(Icon = R.drawable.cloudy, Name ="Weather" )
        Divider(modifier = Modifier.padding(start = 8.dp, end = 8.dp,), thickness =5.dp, color = Color.Black)
              Spacer(modifier = Modifier.height(16.dp))
        Text(text = "To use this App Click on Start Button")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {navController.navigate(Screen.DrawerItem.ShopingList.Route)}, colors= ButtonDefaults.buttonColors(
            Color.Black
        )) {
            Text(text = "START", color = Color.White)
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription =null, tint = Color.White)
        }

        }

    }

@Composable
fun Displayicon(@DrawableRes Icon:Int, Name:String){
    Row(modifier = Modifier
        .fillMaxWidth(), horizontalArrangement = Arrangement.Center){
        Image(painter = painterResource(id = Icon), contentDescription =null, modifier = Modifier.size(width = 100.dp, height = 100.dp))
       Spacer(modifier = Modifier.width(8.dp))
       Box(modifier = Modifier.size(100.dp), contentAlignment = Alignment.Center){
           Text(text = Name)
       }
    }
}