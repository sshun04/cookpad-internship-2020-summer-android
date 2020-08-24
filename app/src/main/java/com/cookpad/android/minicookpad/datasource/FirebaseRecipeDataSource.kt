package com.cookpad.android.minicookpad.datasource

import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRecipeDataSource:
    RecipeDataSource {
    private val db = FirebaseFirestore.getInstance()

    override fun fetchAll(onSuccess: (List<RecipeEntity>) -> Unit, onFailed: (Throwable) -> Unit) {
        db.collection("recipes")
            .get()
            .addOnSuccessListener { result ->
                // アダプタに渡すかわりにコールバックに受け渡すよう変更
                onSuccess.invoke(result.mapNotNull { RecipeEntity.fromDocument(it) })
            }
            .addOnFailureListener(onFailed)
    }
}