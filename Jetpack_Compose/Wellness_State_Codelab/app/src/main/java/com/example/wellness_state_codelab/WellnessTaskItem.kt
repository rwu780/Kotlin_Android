package com.example.wellness_state_codelab

import android.inputmethodservice.Keyboard
import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wellness_state_codelab.ui.theme.Wellness_State_CodelabTheme

@Composable
fun WellnessTaskItem(
    taskName: String,
    modifier: Modifier = Modifier,
    onClose: () -> Unit
) {

    var checkedState by rememberSaveable { mutableStateOf(false)}

    val context = LocalContext.current
    WellnessTaskItem(
        taskName = taskName,
        checked = checkedState,
        onCheckedChange = { newValue ->
            checkedState = newValue
            Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show() },
        onClose = onClose,
        modifier = modifier
    )

}

@Composable
fun WellnessTaskItem(
    taskName: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = taskName
        )

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange)
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")

        }
    }
}

@Preview(showBackground = true)
@Composable
fun WellnessTaskItemPreview() {
    Wellness_State_CodelabTheme() {
        WellnessTaskItem(taskName = "Task1", checked = true, onCheckedChange = {}, onClose = {  })

    }

}