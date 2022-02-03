package com.pseandroid2.dailydata.ui.server.templates

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.pseandroid2.dailydata.ui.composables.ProjectTemplateDialog
import com.pseandroid2.dailydata.ui.composables.ServerCard
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

    if(posts.isNotEmpty() && posts[viewModel.dialogTemplateIndex].projectTemplate != null) {
        val template = posts[viewModel.dialogTemplateIndex].projectTemplate!!
        ProjectTemplateDialog(
            isOpen = viewModel.isProjectTemplateDialogOpen,
            onDismissRequest = { viewModel.onEvent(ServerTemplateScreenEvent.OnCloseDialog) },
            onIconClick = { viewModel.onEvent(ServerTemplateScreenEvent.OnGraphTemplateDownload(projectId = template.id, graphId = template.graphTemplates[it].id)) },
            template = template
        )
    }

    LazyColumn {
        itemsIndexed(posts) { index, post ->
             ServerCard(
                 title = post.title,
                 image = post.image.asImageBitmap(),
                 imageClickable = false,
                 onImageClick = {
                     if (post.graphTemplate == null) {
                         viewModel.onEvent(ServerTemplateScreenEvent.OnShowDialog(index = index))
                     }
                 },
                 onIconClick = {
                     viewModel.onEvent(ServerTemplateScreenEvent.OnTemplateDownload(post.id))
                 }
             )
        }
    }
}