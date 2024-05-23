package com.example.notehub.ui.screens

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notehub.constants.AUTHENTICATION
import com.example.notehub.constants.AUTHORS
import com.example.notehub.constants.ENTER_ARRAY
import com.example.notehub.constants.FILE_ITEM_HEIGHT
import com.example.notehub.constants.FILE_ITEM_RADIUS
import com.example.notehub.constants.SWITCH_THEME
import com.example.notehub.viewmodels.SettingsViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun SettingsScreen(
   // navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var isDarkTheme by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val isLoggedIn = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {
        isLoggedIn.value = isLoggedIn(context)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 58.dp)
    ) {
        ThemeSwitchBar(
            isDarkTheme = isDarkTheme,
            onThemeSwitched = { isChecked -> isDarkTheme = isChecked }
        )
        //if (isLoggedIn.value) {
        if (FirebaseAuth.getInstance().currentUser != null) {
            Text(text = "ТЫ блИн хорош, вошел в Приложуху через гуГлЧанСкий!!1!",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp,
                fontWeight = FontWeight(600),)
            FirebaseAuth.getInstance().currentUser?.uid?.let { uid ->
                //viewModel.uploadToStorage(uid)
                viewModel.downloadFromStorage(uid)
            }
        } else {
            Authorization(onLoginClick = {
                coroutineScope.launch {
                    setSignIn(context, coroutineScope)
                    isLoggedIn.value = isLoggedIn(context)
                    FirebaseAuth.getInstance().currentUser?.uid?.let { uid ->
                        viewModel.uploadToStorage(uid)
                    }
                }
            })
        }
        Text(
            text = AUTHORS,
            fontSize = 20.sp,
            fontWeight = FontWeight(600),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
object PreferencesKeys {
    val LOGGED_IN = booleanPreferencesKey("logged_in")
}
suspend fun setLoggedIn(context: Context, loggedIn: Boolean) {
    context.applicationContext.dataStore.edit { settings ->
        settings[PreferencesKeys.LOGGED_IN] = loggedIn
    }
}
suspend fun isLoggedIn(context: Context): Boolean {
    val preferences = context.applicationContext.dataStore.data.first()
    return preferences[PreferencesKeys.LOGGED_IN] ?: false
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
fun setSignIn(context: Context, coroutineScope: CoroutineScope) {
    val credentialManager = CredentialManager.create(context)

    val rawNonce = UUID.randomUUID().toString()
    val bytes = rawNonce.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }

    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId("918902637511-bcu3r4ccisspl6k5jf2udmj5hsovus3n.apps.googleusercontent.com")
        .setNonce(hashedNonce)
        .build()

    val request: GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    coroutineScope.launch {
        try {
            val result = credentialManager.getCredential(
                request = request,
                context = context
            )
            val credential = result.credential
            val googleIdTokenCredential = GoogleIdTokenCredential
                .createFrom(credential.data)
            val googleIdToken = googleIdTokenCredential.idToken
            Log.i("EBANIY GOOGLE", googleIdToken)
            Toast.makeText(context, "You are signed in!", Toast.LENGTH_SHORT).show()

            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
            FirebaseAuth.getInstance().signInWithCredential(firebaseCredential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.i("FirebaseAuth", "signInWithCredential:success")
                        Toast.makeText(context, "You are signed in!", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("FirebaseAuth", "signInWithCredential:failure", task.exception)
                        Toast.makeText(context, "Failed to sign in!", Toast.LENGTH_SHORT).show()
                    }
                }
            setLoggedIn(context, true)
        } catch (e: Exception) {
            Log.e("LoginError", "Failed to log in", e)
            Toast.makeText(context, "Failed to sign in!", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun Authorization(onLoginClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(FILE_ITEM_HEIGHT)
                .clip(shape = RoundedCornerShape(FILE_ITEM_RADIUS))
                .background(MaterialTheme.colorScheme.surface)
                .clickable { onLoginClick() }
        ) {
            Text(
                text = AUTHENTICATION,
                fontSize = 20.sp,
                fontWeight = FontWeight(600),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            )
            Icon(
                painter = painterResource(id = ENTER_ARRAY),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .padding(end = 12.dp)
            )
        }
    }
}

@Composable
fun ThemeSwitchBar(
    isDarkTheme: Boolean,
    onThemeSwitched: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(FILE_ITEM_HEIGHT)
            .clip(shape = RoundedCornerShape(FILE_ITEM_RADIUS))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = SWITCH_THEME,
            fontSize = 20.sp,
            fontWeight = FontWeight(600),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
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
            modifier = Modifier
                .size(36.dp)
                .padding(end = 12.dp)
        )
    }
}