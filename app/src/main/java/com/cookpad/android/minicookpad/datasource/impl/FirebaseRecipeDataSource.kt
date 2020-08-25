package com.cookpad.android.minicookpad.datasource.impl

import com.cookpad.android.minicookpad.datasource.RecipeDataSource
import com.cookpad.android.minicookpad.datasource.entity.RecipeEntity
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRecipeDataSource : RecipeDataSource {
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

    override fun addRecipe(
        recipe: RecipeEntity,
        onSuccess: () -> Unit,
        onFailed: (Throwable) -> Unit
    ) {
        db.collection("recipes").add(recipe.toMap())
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener(onFailed)
    }
}