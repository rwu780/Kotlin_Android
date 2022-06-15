package com.example.tasklist_animation_codelab.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasklist_animation_codelab.R
import com.example.tasklist_animation_codelab.ui.theme.Green800
import com.example.tasklist_animation_codelab.ui.theme.Purple700
import com.example.tasklist_animation_codelab.ui.theme.TaskList_Animation_CodelabTheme

enum class TabPage {
    Home, Work
}

@Composable
fun HomeTopTabBar(
    backgroundColor: Color,
    tabPage: TabPage,
    onTabSelect: (tabPage: TabPage) -> Unit
) {
    TabRow(
        selectedTabIndex = tabPage.ordinal,
        backgroundColor = backgroundColor,
        indicator = { tabPositions ->  
            HomeTabIndicator(tabPosition = tabPositions, tabPage = tabPage)
        }
    ) {
        HomeTab(
            icon = Icons.Default.Home,
            title = stringResource(id = R.string.home),
            onClick= { onTabSelect(TabPage.Home) }
        )
        
        HomeTab(
            icon = Icons.Default.AccountBox,
            title = stringResource(id = R.string.work),
            onClick={ onTabSelect(TabPage.Work) }
        )
        
    }

}

@Composable
fun HomeTab(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .height(50.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title)
    }
    
}

@Composable
private fun HomeTabIndicator(
    tabPosition: List<TabPosition>,
    tabPage: TabPage
) {
    
    val indicatorLeft = tabPosition[tabPage.ordinal].left
    val indicatorRight = tabPosition[tabPage.ordinal].right
    val color = if (tabPage == TabPage.Home) Purple700 else Green800
    
    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .padding(4.dp)
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, color),
                RoundedCornerShape(4.dp)
            )
    )
    
}

@Preview
@Composable
private fun HomeTopTabBarPreview() {
    TaskList_Animation_CodelabTheme {
        val topPageState by remember { mutableStateOf(TabPage.Work) }

        HomeTopTabBar(tabPage = topPageState, backgroundColor = MaterialTheme.colors.background, onTabSelect = {})
    }
    
}

@Preview
@Composable
private fun HomeTabPreview() {
    TaskList_Animation_CodelabTheme {
        HomeTab(icon = Icons.Default.Home, title = stringResource(id = R.string.home), onClick = {})
    }
    
}

