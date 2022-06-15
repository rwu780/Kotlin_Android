package com.example.wellness_state_codelab

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    viewModel: WellnessViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Column(modifier = modifier) {
        WaterCounter()

//        val list = remember { getWellnessTasks().toMutableStateList()}
        val context = LocalContext.current
        WellnessTasksList(
            list = viewModel.tasks,
            onCloseTask = { task ->
                viewModel.remove(task)
                Toast.makeText(context, "Removing ${task.id} ${task.label}", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

