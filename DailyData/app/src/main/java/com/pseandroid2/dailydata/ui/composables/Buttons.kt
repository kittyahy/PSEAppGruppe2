package com.pseandroid2.dailydata.ui.composables

import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun SaveButton(
    text: String,
    onClick : () -> Unit,
    icon : ImageVector = Icons.Filled.Add
) {
    ExtendedFloatingActionButton(
        text = { Text(text = text) },
        onClick = onClick,
        icon ={
            Icon(
                imageVector = icon,
                contentDescription = "",
                tint = MaterialTheme.colors.onPrimary
            )
        }
    )
}