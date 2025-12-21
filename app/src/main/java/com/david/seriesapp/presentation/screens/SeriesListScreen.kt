package com.david.seriesapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width

import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.david.seriesapp.R
import com.david.seriesapp.viewmodel.ViewModelList

@Composable
fun ViewList (navController: NavController, viewModelList: ViewModelList){

    Column() {
        ListItem(
            headlineContent = { Text("One line list item with 24x24 icon") },
            leadingContent = {
                Image(
                    painter = painterResource(id = R.drawable.images),
                    contentDescription = null,
                )
            }

        )

        HorizontalDivider(modifier = Modifier.width(80.dp))
        ElevatedButton(onClick = {  }) {
            Text("Elevated")
        }
    }
}