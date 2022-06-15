package com.example.wellness_state_codelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterCounter(
    modifier: Modifier = Modifier
) {
    var count by rememberSaveable { mutableStateOf(0) }


    StatelessCounter(onIncrement = { count++ }, count = count, modifier = modifier)
}

@Composable
private fun StatelessCounter(
    modifier: Modifier = Modifier,
    onIncrement: () -> Unit,
    count: Int
){
    Column(
        modifier = modifier.padding(16.dp)
    ) {

        if ( count > 0){
            Text(
                text = "You've had $count glasses.",
            )
        }

        Button(
            onClick= onIncrement, // onClick = { count.value++ },
            enabled = count < 10,
            modifier = Modifier.padding(top = 8.dp)
        ){
            Text("Add one")
        }
    }
}