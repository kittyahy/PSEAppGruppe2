package com.pseandroid2.dailydata.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun ServerCard(
    title : String,
    image : ImageBitmap,
    imageClickable : Boolean,
    onImageClick : () -> Unit = { },
    onIconClick : () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .width(400.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = 10.dp
    ) {
        Column (
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ){

            Image(
                bitmap = image,
                contentDescription = "Text 2",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = imageClickable, onClick = onImageClick)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = title)
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = "",
                    modifier = Modifier.clickable { onIconClick() }
                )
            }
        }
    }
}