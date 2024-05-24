package com.example.notehub.ui.dialogs

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PhotoGalleryBottomSheetContent(onPhotoClicked: (String) -> Unit) {
    val context = LocalContext.current
    val contentResolver = context.contentResolver

    val imageUrls by remember { mutableStateOf(getImageUrls(contentResolver)) }

    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(items = imageUrls) { imageUrl ->
            GlideImage(
                model = imageUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(4.dp)
                    .aspectRatio(1f)
                    .size(120.dp)
                    .clickable { onPhotoClicked(imageUrl) }
                    .clip(shape = RoundedCornerShape(4.dp)),
                contentDescription = null)
        }
    }

}

fun getImageUrls(contentResolver: ContentResolver): List<String> {
    val imageUrls = mutableListOf<String>()

    // Запрос данных изображений из медиа-хранилища
    val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    val projection = arrayOf(MediaStore.Images.Media._ID)
    val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
    val cursor = contentResolver.query(uri, projection, null, null, sortOrder)

    cursor?.use { cursor ->
        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
        while (cursor.moveToNext()) {
            // Получаем ID изображения
            val id = cursor.getLong(idColumn)
            // Формируем URL из ID
            val imageUrl = Uri.withAppendedPath(uri, id.toString()).toString()
            // Добавляем URL в список
            imageUrls.add(imageUrl)
        }
    }

    return imageUrls
}