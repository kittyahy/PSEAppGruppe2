/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/

package com.pseandroid2.dailydata

import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.pseandroid2.dailydata.remoteDataSource.appLinks.JoinProjectLinkManager
import com.pseandroid2.dailydata.ui.theme.DailyDataTheme
import com.pseandroid2.dailydata.ui.composables.BottomNavItem
import com.pseandroid2.dailydata.ui.composables.BottomNavigationBar
import com.pseandroid2.dailydata.util.ui.Navigation
import com.pseandroid2.dailydata.util.ui.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val joinProjectLinkManager = JoinProjectLinkManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyDataTheme {
                Main()
            }
        }
        joinProjectLink()
    }

    /**
     * Checks if the app was started by a dynamic link.
     * If it was started by an correct link it will call openJoinProjectScreen()
     */
    private fun joinProjectLink() {
        val dynamicLink = Firebase.dynamicLinks.getDynamicLink(intent).addOnSuccessListener(this) { pendingDynamicLinkData ->
            // Get deep link from result (may be null if no link is found)
            var deepLink: Uri? = null
            if (pendingDynamicLinkData != null) {
                deepLink = pendingDynamicLinkData.link
            }

            if (deepLink != null) {
                // get projectID from link
                var projectIDString: String = deepLink.getQueryParameter("projectid") ?: "-1" // -1 is an invalid projectID
                val projectID: Long = joinProjectLinkManager.decodePostID(projectIDString)

                // open join project screen when project id is valid
                if (projectID > 0) {
                    openJoinProjectScreen(projectID)
                }
            } else {
                Log.d("Link", "no projectID in link")
            }

        } .addOnFailureListener(this) { e -> Log.w("DynamicLink", "getDynamicLink:onFailure", e) }
    }

    /**
     * Loads the the screen where user can join a project
     */
    private fun openJoinProjectScreen(projectID: Long) {
        // TODO: Implement this method
        Log.d("Link", "open join project screen with project id: $projectID")
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

