package com.example.notehub.ui.bars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.notehub.R
import com.example.notehub.constants.NOTE_TITLE_SIZE
import com.example.notehub.constants.TOP_BAR_HEIGHT

@Composable
fun EditorBar(
    title: String,
    isEditMode: Boolean,
    onEditClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_BAR_HEIGHT)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = title,
            fontSize = NOTE_TITLE_SIZE,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Row {
            IconButton(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        shape = RoundedCornerShape(10.dp),
                        color = if(isEditMode) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent
                    ),
                onClick = { onEditClick() },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                modifier = Modifier.size(44.dp),
                onClick = { }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.example),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}