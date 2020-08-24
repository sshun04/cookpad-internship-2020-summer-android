package com.cookpad.android.minicookpad.datasource.impl

import android.net.Uri
import com.cookpad.android.minicookpad.datasource.ImageDataSource
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class FirebaseImageDataSource : ImageDataSource {
    private val reference = FirebaseStorage.getInstance().reference

    override fun saveImage(
        uri: String,
        onSuccess: (String) -> Unit,
        onFailed: (Throwable) -> Unit
    ) {
        val imageRef = "$COLLECTION_PATH/${UUID.randomUUID()}"
        reference.child(imageRef).putFile(Uri.parse(uri))
            .addOnSuccessListener { onSuccess.invoke(imageRef) }
            .addOnFailureListener(onFailed)
    }

    private companion object {
        const val COLLECTION_PATH = "images"
    }
}