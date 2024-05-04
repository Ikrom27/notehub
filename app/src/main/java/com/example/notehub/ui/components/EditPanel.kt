package com.example.notehub.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.example.notehub.R
import com.example.notehub.constants.EDIT_PANEL_HEIGHT
import com.example.notehub.constants.ICON_MEDIUM_PLUS
import com.example.notehub.constants.SPACER_EXTRA_SMALL
import com.example.notehub.utils.MarkdownUtils

data class EditPanelButton(
    val icon: Int,
    val onClick: (TextFieldValue, Boolean) -> TextFieldValue,
    val selected: (TextFieldValue) -> Boolean
)

@Composable
fun EditPanel(
    modifier: Modifier = Modifier,
    textFieldValue: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit
) {
    Column(
        modifier = modifier
            .imePadding()
            .fillMaxWidth()
            .height(EDIT_PANEL_HEIGHT)
            .background(MaterialTheme.colorScheme.background),
    ) {
        SeparatorDecoration()
        LazyRow(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(SPACER_EXTRA_SMALL)
        ) {
            items(items = EDIT_PANEL_BUTTONS){ button ->
                val selected = button.selected(textFieldValue)
                SelectableButton(
                    icon = button.icon,
                    selected = button.selected(textFieldValue),
                    size = ICON_MEDIUM_PLUS
                ) {
                    onTextChange(button.onClick(textFieldValue, selected))
                }
            }
            item {
                SelectableButton(
                    icon = R.drawable.ic_edit,
                    selected = false,
                    size = ICON_MEDIUM_PLUS
                ) {

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

