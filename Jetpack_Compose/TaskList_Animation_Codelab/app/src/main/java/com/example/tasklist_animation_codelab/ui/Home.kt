package com.example.tasklist_animation_codelab.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasklist_animation_codelab.R
import com.example.tasklist_animation_codelab.ui.theme.Green300
import com.example.tasklist_animation_codelab.ui.theme.Purple100
import com.example.tasklist_animation_codelab.ui.theme.TaskList_Animation_CodelabTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeApp() {

    val scope = rememberCoroutineScope()

    var tabPage by remember { mutableStateOf( TabPage.Home) }

    val backgroundColor = if (tabPage == TabPage.Home) Purple100 else Green300

    var editMessageShown by remember { mutableStateOf(false)}

    suspend fun showEditMessage() {
        if (!editMessageShown){
            editMessageShown = true
            delay(3000L)
            editMessageShown = false
        }
    }

    Scaffold(
        topBar = {
            HomeTopTabBar(
                backgroundColor = backgroundColor,
                tabPage = tabPage,
                onTabSelect = { tabPage = it}
            )
        },
        backgroundColor = backgroundColor,
        floatingActionButton = {
            HomeFloatingActionButton(
                extended = false,
                onClick = {
                    scope.launch {
                        showEditMessage()
                    }

            })
        }
    ) {

    }

}


@Preview
@Composable
fun HomeAppPreview() {

    TaskList_Animation_CodelabTheme {
        HomeApp()
    }

}

@Composable
fun HomeFloatingActionButton(
    extended: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(onClick = onClick) {
        Row(
            modifier = modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null
            )

            if (extended) {
                Text(
                    text = stringResource(R.string.edit),
                    modifier = Modifier.padding(start = 8.dp, top = 3.dp)

                )
            }
        }

    }
}