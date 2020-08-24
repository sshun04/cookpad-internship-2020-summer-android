package com.cookpad.android.minicookpad.datasource

import com.cookpad.android.minicookpad.datasource.entity.RecipeEntity

interface RecipeDataSource {
    fun fetchAll(onSuccess: (List<RecipeEntity>) -> Unit, onFailed: (Throwable) -> Unit)
    fun addRecipe(recipe: RecipeEntity, onSuccess: () -> Unit, onFailed: (Throwable) -> Unit)
}