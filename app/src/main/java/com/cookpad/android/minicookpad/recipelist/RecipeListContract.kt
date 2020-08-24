package com.cookpad.android.minicookpad.recipelist

interface RecipeListContract {
    interface View {
        fun renderRecipeList(recipeList: List<RecipeListContract.Recipe>)
        fun renderError(exception: Throwable)
    }

    interface Interactor {
        fun fetchRecipeList(
            onSuccess: (List<RecipeListContract.Recipe>) -> Unit,
            onFailed: (Throwable) -> Unit
        )
    }

    interface Presenter {
        fun onRecipeListRequested()
        fun onRecipeDetailRequested(recipeId: String, recipeName: String)
    }

    interface Routing {
        fun navigateRecipeDetail(recipeId: String, recipeName: String)
    }

    data class Recipe(
        val id: String,        // レシピのID
        val title: String,     // レシピのタイトル
        val imagePath: String, // Firebase Storage 上のパス: "images/hogehoge.png"
        val steps: String,     // 作り方のテキストをまとめたもの
        val authorName: String // 作者名
    )
}