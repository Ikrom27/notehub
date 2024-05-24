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
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import com.example.notehub.R
import com.example.notehub.constants.EDIT_PANEL_HEIGHT
import com.example.notehub.constants.ICON_MEDIUM_PLUS
import com.example.notehub.constants.LABEL_HEADER1
import com.example.notehub.constants.LABEL_HEADER2
import com.example.notehub.constants.LABEL_HEADER3
import com.example.notehub.constants.LABEL_NONE
import com.example.notehub.constants.SPACER_EXTRA_SMALL
import com.example.notehub.utils.DimensCalculator
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
    var showLinkDialog by remember {
        mutableStateOf(false)
    }

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
                IconButton(onClick = { showLinkDialog = true}) {
                    Icon(painter = painterResource(
                        id = R.drawable.ic_link),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null,
                        modifier = Modifier.height(DimensCalculator.calculateIconSize(ICON_MEDIUM_PLUS))
                    )
                }
                FontSizeButton(onItemClick = {headerNum ->
                    onTextChange(MarkdownUtils.makeHeader(textFieldValue, headerNum))
                })
            }
        }
    }
    if (showLinkDialog){
        SetNameDialog(onDismissRequest = {
            showLinkDialog = false
        }) {
            onTextChange(MarkdownUtils.makeLink(textFieldValue, it))
        }
    }
}

@Composable
fun FontSizeButton(
    onItemClick: (Int) -> Unit
){
    DropdownButton(
        item = {
            Icon(painter = painterResource(
                id = R.drawable.ic_font_size),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
                modifier = Modifier.height(DimensCalculator.calculateIconSize(ICON_MEDIUM_PLUS))
            )
        }
    ) {
        DropdownMenuItem(
            text = { Text(LABEL_NONE) },
            onClick = {
                onItemClick(0)
            }
        )
        DropdownMenuItem(
            text = { Text(LABEL_HEADER1) },
            onClick = {
                onItemClick(1)
            }
        )
        DropdownMenuItem(
            text = { Text(LABEL_HEADER2) },
            onClick = {
                onItemClick(2)
            }
        )
        DropdownMenuItem(
            text = { Text(LABEL_HEADER3) },
            onClick = {
                onItemClick(3)
            }
        )
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
    ),
    EditPanelButton(
        icon = R.drawable.ic_marked_list,
        onClick = { value, _ -> MarkdownUtils.makeMarkedList(value) },
        selected = { false }
    ),
)

