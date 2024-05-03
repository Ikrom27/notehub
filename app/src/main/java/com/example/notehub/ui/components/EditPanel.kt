package com.example.notehub.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import com.example.notehub.R
import com.example.notehub.constants.EDIT_PANEL_HEIGHT
import com.example.notehub.constants.ICON_MEDIUM
import com.example.notehub.constants.ICON_MEDIUM_PLUS
import com.example.notehub.constants.MINIMAL_HEIGHT
import com.example.notehub.constants.SEPARATOR_COLOR
import com.example.notehub.utils.MarkdownUtils

data class EditPanelButton(
    val icon: Int,
    val onClick: (TextFieldValue, Boolean) -> String,
    val selected: (TextFieldValue) -> Boolean
)

@Composable
fun EditPanel(
    modifier: Modifier = Modifier,
    textFieldValue: TextFieldValue,
    onTextChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .imePadding()
            .fillMaxWidth()
            .height(EDIT_PANEL_HEIGHT)
            .background(MaterialTheme.colorScheme.background),
    ) {
        Box(modifier.fillMaxWidth().height(MINIMAL_HEIGHT).background(SEPARATOR_COLOR))
        LazyRow(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(items = EDIT_PANEL_BUTTONS){ button ->
                val selected = button.selected(textFieldValue)
                IconButton(
                    onClick = { onTextChange(button.onClick(textFieldValue, selected)) },
                    modifier = Modifier
                        .height(ICON_MEDIUM_PLUS)
                        .background(color = if(selected) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent)
                ) {
                    Icon(painter = painterResource(
                        id = button.icon),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null,
                        modifier = Modifier.height(ICON_MEDIUM)
                    )
                }
            }
            item {
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .height(ICON_MEDIUM_PLUS)
                ) {
                    Icon(painter = painterResource(
                        id = R.drawable.ic_edit),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null,
                        modifier = Modifier.height(ICON_MEDIUM)
                    )
                }
            }
        }
    }
}

val EDIT_PANEL_BUTTONS = listOf(
    EditPanelButton(
        icon = R.drawable.ic_italic,
        onClick = { value, selected -> MarkdownUtils.toggleItalic(value, selected) },
        selected = { MarkdownUtils.isItalic(it) }
    ),
    EditPanelButton(
        icon = R.drawable.ic_bold,
        onClick = { value, selected -> MarkdownUtils.toggleBold(value, selected) },
        selected = { MarkdownUtils.isBold(it) }
    ),
    EditPanelButton(
        icon = R.drawable.ic_strikethrough,
        onClick = { value, selected -> MarkdownUtils.toggleStrikethrough(value, selected) },
        selected = { MarkdownUtils.isStrikethrough(it) }
    ),
    EditPanelButton(
        icon = R.drawable.ic_code,
        onClick = { value, selected -> MarkdownUtils.toggleCode(value, selected) },
        selected = { MarkdownUtils.isCode(it) }
    )
)

