package com.example.notehub.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notehub.constants.AUTHORS
import com.example.notehub.constants.ENTER_ARRAY
import com.example.notehub.constants.FILE_ITEM_HEIGHT
import com.example.notehub.constants.FILE_ITEM_RADIUS
import com.example.notehub.constants.SWITCH_THEME
import com.example.notehub.viewmodels.SettingsViewModel


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var isDarkTheme by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 58.dp)
    ) {
        ThemeSwitchBar(
            isDarkTheme = isDarkTheme,
            onThemeSwitched = { isChecked -> isDarkTheme = isChecked }
        )
        Authorization(
            onLoginClick = {
                viewModel.loginByGoogle(context, coroutineScope)
                Toast.makeText(context, "Login", Toast.LENGTH_SHORT).show()
            }
        )
        Text(text = AUTHORS,
            fontSize = 20.sp,
            fontWeight = FontWeight(600),
            color = MaterialTheme.colorScheme.primary)

    }
}

@Composable
fun Authorization(onLoginClick: () -> Unit){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                onLoginClick()
            }) {
            Text(text = "Login")
        }
    }
}

@Composable
fun ThemeSwitchBar(
    isDarkTheme: Boolean,
    onThemeSwitched: (Boolean) -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(FILE_ITEM_HEIGHT)
            .clip(shape = RoundedCornerShape(FILE_ITEM_RADIUS))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Text(
            text = SWITCH_THEME,
            fontSize = 20.sp,
            fontWeight = FontWeight(600),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = ENTER_ARRAY),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Switch(
            checked = isDarkTheme,
            onCheckedChange = { isChecked -> onThemeSwitched(isChecked) },
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Icon(
            painter = painterResource(id = ENTER_ARRAY),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
    }
}