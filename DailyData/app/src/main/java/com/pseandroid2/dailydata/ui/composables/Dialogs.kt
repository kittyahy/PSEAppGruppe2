package com.pseandroid2.dailydata.ui.composables

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ProjectTemplate

@Composable
fun ProjectTemplateDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onIconClick: (Int) -> Unit,
    template: ProjectTemplate
) {
    if (isOpen) {
        Dialog(
            onDismissRequest = onDismissRequest
        ) {
            Surface(
                shape = RoundedCornerShape(5.dp),
                color = MaterialTheme.colors.background,
                modifier = Modifier.padding(10.dp)
            ) {
                LazyColumn {

                    itemsIndexed(template.graphTemplates) { index, graphTemplate ->
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Image(
                                bitmap = graphTemplate.image?.asImageBitmap()
                                    ?: TODO("Probably should include some kind of fallback"),
                                contentDescription = "",
                                modifier = Modifier.fillMaxWidth()
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = graphTemplate.title)
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "",
                                    modifier = Modifier.clickable { onIconClick(index) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}