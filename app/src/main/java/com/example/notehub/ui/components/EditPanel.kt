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
    val onClick: (TextFieldValue) -> String
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
                IconButton(
                    onClick = { onTextChange(button.onClick(textFieldValue)) },
                    modifier = Modifier.height(ICON_MEDIUM_PLUS)
                ) {
                    Icon(painter = painterResource(
                        id = button.icon),
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
        onClick = {MarkdownUtils.makeItalic(it)}
    ),
    EditPanelButton(
        icon = R.drawable.ic_bold,
        onClick = {MarkdownUtils.makeBold(it)}
    ),
    EditPanelButton(
        icon = R.drawable.ic_strikethrough,
        onClick = {MarkdownUtils.makeStrikethrough(it)}
    ),
    EditPanelButton(
        icon = R.drawable.ic_code,
        onClick = {MarkdownUtils.makeCode(it)}
    )
)