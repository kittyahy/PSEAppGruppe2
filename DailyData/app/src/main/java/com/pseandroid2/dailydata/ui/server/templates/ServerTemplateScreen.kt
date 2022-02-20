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

package com.pseandroid2.dailydata.ui.server.templates

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pseandroid2.dailydata.ui.composables.PreviewCard
import com.pseandroid2.dailydata.ui.composables.ProjectTemplateDialog
import com.pseandroid2.dailydata.util.ui.UiEvent


@Composable
fun ServerTemplatesScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ServerTemplateScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val posts = viewModel.posts

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                else -> { }
            }
        }
    }

    if(posts.isNotEmpty()) {
        val templates = viewModel.post.postEntries
        ProjectTemplateDialog(
            isOpen = viewModel.isProjectTemplateDialogOpen,
            onDismissRequest = { viewModel.onEvent(ServerTemplateScreenEvent.OnCloseDialog) },
            onIconClick = { viewModel.onEvent(ServerTemplateScreenEvent.OnPostEntryDownload(id = it)) },
            templates = templates
        )
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(posts) { index, post ->
             PreviewCard(
                 title = post.title,
                 image = post.image.asImageBitmap(),
                 imageClickable = true,
                 onImageClick = { viewModel.onEvent(ServerTemplateScreenEvent.OnShowDialog(index = index)) },
                 onIconClick = { viewModel.onEvent(ServerTemplateScreenEvent.OnPostDownload(id = post.id)) },
                 icon = Icons.Default.Download
             )
        }
    }
}