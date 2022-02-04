package com.pseandroid2.dailydata.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.PostEntry

@Composable
fun ProjectTemplateDialog(
    isOpen : Boolean,
    onDismissRequest : () -> Unit,
    onIconClick: (id : Int) -> Unit,
    templates : List<PostEntry>
) {
    if(isOpen) {
        Dialog(
            onDismissRequest = onDismissRequest
        ) {
            Surface(
                shape = RoundedCornerShape(5.dp),
                color = MaterialTheme.colors.background,
                modifier = Modifier.padding(10.dp)
            ) {
                LazyColumn {
                    items(templates) { postEntry ->
                        Column (
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ){
                            Image(
                                bitmap = postEntry.detailImage.asImageBitmap(),
                                contentDescription = "",
                                modifier = Modifier.fillMaxWidth()
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = postEntry.title)
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "",
                                    modifier = Modifier.clickable { onIconClick(postEntry.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}