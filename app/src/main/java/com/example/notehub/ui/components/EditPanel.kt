package com.example.notehub.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import com.example.notehub.R
import com.example.notehub.constants.TOP_BAR_HEIGHT
import com.example.notehub.utils.MarkdownUtils

@Composable
fun EditPanel(
    modifier: Modifier = Modifier,
    textFieldValue: TextFieldValue,
    onTextChange: (String) -> Unit
) {


    Row(
        modifier = modifier
            .imePadding()
            .fillMaxWidth()
            .height(TOP_BAR_HEIGHT)
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyRow {
            item {
                IconButton(onClick = {
                    onTextChange(MarkdownUtils.setBold(textFieldValue))
                }) {
                    Icon(painter = painterResource(
                        id = R.drawable.ic_bold),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null)
                }
            }
        }
    }
}