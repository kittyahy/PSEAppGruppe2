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