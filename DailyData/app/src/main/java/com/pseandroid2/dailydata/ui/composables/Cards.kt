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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pseandroid2.dailydata.R

@Composable
fun PreviewCard(
    title : String,
    image : ImageBitmap,
    imageClickable : Boolean,
    onImageClick : () -> Unit = { },
    onIconClick : () -> Unit,
    icon : ImageVector
) {
    Card(modifier = Modifier
        .width(400.dp),
        elevation = 4.dp,
        shape = RectangleShape
    ) {
        Column (
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(bottom = 4.dp)
        ){

            Image(
                bitmap = image,
                contentDescription = "Text 2",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = imageClickable, onClick = onImageClick)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = title)
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    modifier = Modifier.clickable { onIconClick() }
                )
            }
        }
    }
}