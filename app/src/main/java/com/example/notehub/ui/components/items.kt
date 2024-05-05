package com.example.notehub.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notehub.constants.ADD_ICON_TOP_PADDING
import com.example.notehub.constants.ENTER_ARRAY
import com.example.notehub.constants.FILE_ITEMS_BETWEEN_PADDING
import com.example.notehub.constants.FILE_ITEM_HEIGHT
import com.example.notehub.constants.FILE_ITEM_RADIUS
import com.example.notehub.constants.ICON_MEDIUM
import com.example.notehub.constants.NOTE_ITEM_HEIGHT
import com.example.notehub.constants.NOTE_ITEM_RADIUS


@Composable
fun FolderItem(
    title: String,
    counter: Int,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
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
        Icon(
            painter = painterResource(id = ENTER_ARRAY),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null,
            modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun AddIcon(onClick: () -> Unit){
    Box(
        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = ADD_ICON_TOP_PADDING - FILE_ITEMS_BETWEEN_PADDING),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            modifier = Modifier
                .size(ICON_MEDIUM)
                .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape),
            onClick = { onClick() }
        ) {
            /* TODO: add icon */
        }
    }
}

@Composable
fun NoteItem(title: String,
             modifier: Modifier = Modifier,
             noteText: String){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .size(133.dp, NOTE_ITEM_HEIGHT)
            //.height(NOTE_ITEM_HEIGHT)
            .clip(
                shape = RoundedCornerShape(NOTE_ITEM_RADIUS)
            )
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight(600),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth().padding(10.dp,28.dp,10.dp,27.dp)
        )
        Text(
            text = noteText,
            fontSize = 20.sp,
            fontWeight = FontWeight(600),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp)
        )
    }
}

