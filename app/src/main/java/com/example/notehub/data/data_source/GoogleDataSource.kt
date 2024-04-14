package com.example.notehub.data.data_source
import android.content.Context
import androidx.credentials.GetCredentialRequest
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.credentials.CredentialManager
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

class GoogleDataSource {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    fun setSignIn(context: Context, coroutineScope: CoroutineScope){
        val credentialManager = CredentialManager.create(context)

        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold(""){str, it -> str + "%02x".format(it)}

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("1019679628253-5a254upmo8m62v4c7tn2g2pi97lptldq.apps.googleusercontent.com")
            .setNonce(hashedNonce)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        coroutineScope.launch {
            val result = credentialManager.getCredential(
                request = request,
                context = context
            )
            val credential = result.credential
            val googleIdTokenCredential = GoogleIdTokenCredential
                .createFrom(credential.data)
            val googleIdToken = googleIdTokenCredential.idToken
            Log.i("EBANIY GOOGLE", googleIdToken)
            Toast.makeText(context,"You are signed in!", Toast.LENGTH_SHORT).show()
        }
    }

}