package com.example.notehub.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.notehub.constants.ENTER_ARRAY
import com.example.notehub.constants.ICON_SMALL
import com.example.notehub.constants.FILE_ITEM_HEIGHT
import com.example.notehub.constants.FILE_ITEM_RADIUS

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FolderItem(
    title: String,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    counter: Int
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongClick() }
            )
            .fillMaxWidth()
            .height(FILE_ITEM_HEIGHT)
            .clip(
                shape = RoundedCornerShape(FILE_ITEM_RADIUS)
            )
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight(600),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = counter.toString(),
            fontSize = 20.sp,
            fontWeight = FontWeight(600),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        )
        IconButton(
            onClick = { onClick() },
            modifier = Modifier.size(ICON_SMALL)
        ) {
            Icon(
                painter = painterResource(id = ENTER_ARRAY),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
                modifier = Modifier.fillMaxSize())
        }
    }
}