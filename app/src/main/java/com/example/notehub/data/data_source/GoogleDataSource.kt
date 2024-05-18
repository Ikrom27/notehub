package com.example.notehub.data.data_source

import android.net.Uri
import android.os.Environment
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class GoogleDataSource {

    fun uploadFileToFirebaseStorage(userUid: String) {
        val noteHubPath = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "NoteHub")
        if (noteHubPath.exists() && noteHubPath.isDirectory) {
            uploadDirectory(noteHubPath, "users/$userUid/")
        }
    }

    private fun uploadDirectory(directory: File, storagePath: String) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child(storagePath)

        directory.listFiles()?.forEach { file ->
            if (file.isDirectory) {
                uploadDirectory(file, "$storagePath/${file.name}")
            } else {
                val fileUri = Uri.fromFile(file)
                val fileRef = storageRef.child(file.name)
                fileRef.putFile(fileUri)
                    .addOnSuccessListener {
                        Log.i("FirebaseStorage", "File ${file.name} uploaded successfully")
                    }
                    .addOnFailureListener { exception ->
                        Log.e("FirebaseStorage", "File ${file.name} upload failed", exception)
                    }
            }
        }
    }

}