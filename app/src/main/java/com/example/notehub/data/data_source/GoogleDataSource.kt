package com.example.notehub.data.data_source

import android.net.Uri
import android.os.Environment
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
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

    fun downloadFilesFromFirebaseStorage(userUid: String) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("users/$userUid/")
        val noteHubPath = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "NoteHub")

        downloadDirectory(storageRef, noteHubPath)
    }

    private fun downloadDirectory(storageRef: StorageReference, localDir: File) {
        localDir.mkdirs()
        storageRef.listAll()
            .addOnSuccessListener { listResult ->
                listResult.prefixes.forEach { folderRef ->
                    val localSubDir = File(localDir, folderRef.name)
                    downloadDirectory(folderRef, localSubDir)
                }
                listResult.items.forEach { fileRef ->
                    val localFile = File(localDir, fileRef.name)
                    downloadFile(fileRef, localFile)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("FirebaseStorage", "Failed to list directory contents", exception)
            }
    }

    private fun downloadFile(fileRef: StorageReference, localFile: File) {
        fileRef.getFile(localFile)
            .addOnSuccessListener {
                Log.i("FirebaseStorage", "File ${fileRef.name} downloaded successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("FirebaseStorage", "File ${fileRef.name} download failed", exception)
            }
    }
}