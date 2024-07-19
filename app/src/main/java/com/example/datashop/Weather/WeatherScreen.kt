package com.example.datashop.Weather

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.datashop.R
import com.example.datashop.domain.DrawerItem
import com.example.datashop.screen.DrawerDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun  WeatherScreen(viewModel: WeatherviewModel,navigation:NavController,context: Context) {
    val Scope: CoroutineScope = rememberCoroutineScope()
    val ScaffoldState= rememberScaffoldState()
    var data by remember { mutableStateOf(viewModel.WeatherContent.value.data) }
    var error by remember { mutableStateOf(viewModel.WeatherContent.value.error) }
    Scaffold(
        scaffoldState = ScaffoldState, topBar = {
            TopAppBar(title = {
                Text(
                    text = "Weather Screen",
                    style = androidx.compose.material3.MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold
                )
            }, backgroundColor = colorResource(
                id = R.color.purple_700
            ), navigationIcon = {
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
        drawerContent = {
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
        }, drawerBackgroundColor = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    singleLine = true,
                    value = viewModel.city.value,
                    onValueChange = { viewModel.setCity(it) },
                    label = { Text(text = "Search for any location") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(2.dp))
                IconButton(onClick = {
                    if (viewModel.city.value == "") {
                        Toast.makeText(context, "Enter the name of the city", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        viewModel.Fetchdata(viewModel.city.value)
                    }
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }
            }
                if (viewModel.WeatherContent.value.lodaing) {
                    CircularProgressIndicator()
                }
                else if (viewModel.WeatherContent.value.error!=null) {
                    Text(text = "Error Occoured")
                    Text(text = "$error")
                }
                else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_location_on_24),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(text = "${viewModel.WeatherContent.value.data?.location?.name}", fontSize = 30.sp)
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${viewModel.WeatherContent.value.data?.location?.country}",
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "${viewModel.WeatherContent.value.data?.current?.temp_c}°C ",
                        fontSize = 60.sp,
                        fontWeight = FontWeight.Bold
                    )
                    AsyncImage(
                        model = "https:${viewModel.WeatherContent.value.data?.current?.condition?.icon}".replace(
                            "64x64",
                            "128x128"
                        ), contentDescription = null, modifier = Modifier.size(160.dp)
                    )
                    Text(
                        text = "${viewModel.WeatherContent.value.data?.current?.condition?.text} ",
                        fontSize = 60.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally){
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = "${viewModel.WeatherContent.value.data?.current?.humidity}",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "humidity",
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Gray
                                    )
                                }
                                Column {
                                    Text(
                                        text = "${viewModel.WeatherContent.value.data?.current?.wind_kph}",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Wind Speed(Kph)",
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Gray
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = "${viewModel.WeatherContent.value.data?.current?.uv}",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "UV",
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Gray
                                    )
                                }
                                Column {
                                    Text(
                                        text = "${viewModel.WeatherContent.value.data?.current?.precip_mm}",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Participation(mm)",
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }



                    }
                }
        }

    }
}





























