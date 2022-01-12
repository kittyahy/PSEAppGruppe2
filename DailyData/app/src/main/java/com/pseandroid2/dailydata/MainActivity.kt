package com.pseandroid2.dailydata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.pseandroid2.dailydata.ui.theme.DailyDataTheme
import com.pseandroid2.dailydata.ui.composables.BottomNavItem
import com.pseandroid2.dailydata.ui.composables.BottomNavigationBar
import com.pseandroid2.dailydata.util.ui.Navigation
import com.pseandroid2.dailydata.util.ui.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyDataTheme {
                Main()
            }
        }
    }
}

@Composable
fun Main() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = listOf (
                    BottomNavItem(
                        name = "Project",
                        route = Routes.PROJECT,
                        icon = Icons.Default.Home),
                    BottomNavItem(
                        name = "Templates",
                        route = Routes.TEMPLATES,
                        icon = Icons.Default.Favorite),
                    BottomNavItem(
                        name = "Server",
                        route = Routes.SERVER,
                        icon = Icons.Default.Share),
                ),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            Navigation(navController = navController)
        }
    }
}