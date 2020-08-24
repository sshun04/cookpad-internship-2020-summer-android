package com.cookpad.android.minicookpad.datasource

import com.cookpad.android.minicookpad.datasource.RecipeEntity

interface RecipeDataSource {
    fun fetchAll(onSuccess: (List<RecipeEntity>) -> Unit, onFailed: (Throwable) -> Unit)
}