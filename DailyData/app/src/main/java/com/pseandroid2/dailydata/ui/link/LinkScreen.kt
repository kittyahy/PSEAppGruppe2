package com.pseandroid2.dailydata.ui.link

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun LinkScreen(
    pID : Long,
    onJoinClick : () -> Unit,
    viewModel: LinkScreenViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.padding(vertical = 40.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Green),
            contentAlignment = Alignment.Center
        ) {

        }
        Text(text = "Title")
        Button(onClick = {
            viewModel.onEvent(LinkScreenEvent.OnButtonClick(id = pID))
            onJoinClick()
        }
            ) {
            Text(text = "Join Project")
        }
    }
}