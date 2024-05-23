package com.example.notehub.ui.components

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notehub.constants.ADD_ICON_TOP_PADDING
import com.example.notehub.constants.ENTER_ARRAY
import com.example.notehub.constants.FILE_ITEMS_BETWEEN_PADDING
import com.example.notehub.constants.FILE_ITEM_HEIGHT
import com.example.notehub.constants.FILE_ITEM_RADIUS
import com.example.notehub.constants.ICON_LARGE
import com.example.notehub.constants.NOTE_ITEM_HEIGHT
import com.example.notehub.constants.NOTE_ITEM_RADIUS
import com.example.notehub.constants.NOTE_ITEM_WIDTH
import com.example.notehub.constants.NOTE_ITEM_HORIZONTAL_PADDING
import com.example.notehub.utils.DimensCalculator


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
            .background(MaterialTheme.colorScheme.surface)
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
                .size(ICON_LARGE)
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
            .size(NOTE_ITEM_WIDTH, NOTE_ITEM_HEIGHT)
            .clip(
                shape = RoundedCornerShape(DimensCalculator.calculateRadius(NOTE_ITEM_WIDTH))
            )
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight(600),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth().padding(horizontal = NOTE_ITEM_HORIZONTAL_PADDING, vertical = 12.dp)
        )

        Text(
            text = noteText,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp)
        )
    }
}

@Composable
fun WithMenuItem(
    onItemClick: () -> Unit,
    item: @Composable () -> Unit,
    dropDownItems: @Composable (onClick: () -> Unit) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var pressOffset by remember { mutableStateOf(DpOffset.Zero) }
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    Card(
        modifier = Modifier
            .pointerInput(true) {
                detectTapGestures(
                    onTap = { onItemClick() },
                    onLongPress = { offset ->
                        pressOffset = DpOffset(offset.x.toDp(), offset.y.toDp())
                        expanded = true
                        vibrator.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE))
                    }
                )
            }
    ) {
        item()
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = pressOffset
        ) {
            dropDownItems {
                expanded = false
            }
        }
    }
}


