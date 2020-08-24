package com.cookpad.android.minicookpad.recipecreate

interface RecipeCreateContract {
    interface View {
        fun renderSuccess()
        fun renderError(exception: Throwable)
    }

    interface Interactor {
        fun uploadRecipe(
            recipe: Recipe,
            onSuccess: () -> Unit,
            onFailed: (Throwable) -> Unit
        )
    }

    interface Presenter {
        fun onSaveRequested(recipe: Recipe)
    }

    interface Routing {
        fun navigateRecipeList()
    }

    data class Recipe(
        val title: String,
        val imageUri: String,
        val steps: List<String>
    )
}