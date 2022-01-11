package com.pseandroid2.dailydata.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopNavigationBar(
    items: List<TopNavItem>,
    indexCurrentTab : Int,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit
) {

    TabRow(selectedTabIndex = indexCurrentTab, modifier = modifier) {
        items.forEachIndexed { index, item ->
            val selected = (indexCurrentTab == index)
            Tab(
                selected = selected,
                onClick = onItemClick,
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

data class TopNavItem(
    val name: String,
    val route: String
)