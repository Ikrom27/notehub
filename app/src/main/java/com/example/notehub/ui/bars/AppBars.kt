package com.example.notehub.ui.bars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.notehub.R
import com.example.notehub.constants.ICON_LARGE
import com.example.notehub.constants.MAIN_HORIZONTAL_PADDING
import com.example.notehub.constants.NOTE_TITLE_SIZE
import com.example.notehub.constants.TITLE_SIZE
import com.example.notehub.constants.TOP_APP_HORIZONTAL_PADDING
import com.example.notehub.constants.TOP_BAR_HEIGHT
import com.example.notehub.ui.components.SelectableButton

@Composable
fun EditorBar(
    title: String,
    isEditMode: Boolean,
    onEditClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    var timePickerState by remember { mutableStateOf(false) }

    if (timePickerState) {
        onMoreClick()
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_BAR_HEIGHT)
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = MAIN_HORIZONTAL_PADDING)
    ) {
        Text(
            text = title,
            fontSize = NOTE_TITLE_SIZE,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Row {
            SelectableButton(
                icon = R.drawable.ic_edit,
                selected = isEditMode,
                size = ICON_LARGE
            ) {
                onEditClick()
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                modifier = Modifier.size(44.dp),
                onClick = {
                    onMoreClick()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_more),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    isTrashScreen: Boolean,
    onSettingsClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    val borderColor = MaterialTheme.colorScheme.background.copy(alpha = 0.0f)
    MediumTopAppBar(
        actions = {
            IconButton(
                onClick = {
                    onSettingsClick()
                }) {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "Настройки"
                )
            }
        },
        title = {
            Column {
                /*Row {
                    Text(
                        text = title,
                        fontSize = NOTE_TITLE_SIZE,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (isTrashScreen){
                        Icon(painter = painterResource(id = R.drawable.ic_trash),
                            contentDescription = "TrashScreen")
                    }
                }*/
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp),
                    leadingIcon = {
                        Icon(
                            painterResource(id = R.drawable.ic_search),
                            contentDescription = "leadingIcon"
                        )
                    },
                    shape = RoundedCornerShape(27.dp),
                    placeholder = { Text("Поиск") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        onSearchClick()
                    }),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = borderColor,
                        unfocusedIndicatorColor = borderColor,
                    )
                )
            }
        }
    )

}

@Composable
fun NHTopAppBar(
    title: String,
    isTrashScreen: Boolean,
    onSettingsClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            //.height(TOP_BAR_SEARCH_HEIGHT)
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = TOP_APP_HORIZONTAL_PADDING)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    text = title,
                    fontSize = TITLE_SIZE,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (isTrashScreen) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_trash),
                        contentDescription = "TrashScreen",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            //Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                modifier = Modifier.size(44.dp),
                onClick = {
                    onSettingsClick()
                }
            ) {
                Icon(
                    Icons.Filled.Settings,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth(),
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.ic_search),
                    contentDescription = "leadingIcon",
                    Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            shape = RoundedCornerShape(30.dp),
            placeholder = { Text("Поиск") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearchClick()
            }),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopAppBar() {
    TopAppBar(
        actions = {
            Icon(
                Icons.Filled.Settings, contentDescription = "settings icon",
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = MAIN_HORIZONTAL_PADDING)
            )
        },
        title = {
            Text(text = stringResource(id = R.string.settings), fontSize = TITLE_SIZE,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary)
        },
        colors = TopAppBarColors(
            actionIconContentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}