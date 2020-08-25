package com.cookpad.android.minicookpad.recipecreate

import com.cookpad.android.minicookpad.datasource.ImageDataSource
import com.cookpad.android.minicookpad.datasource.RecipeDataSource
import com.cookpad.android.minicookpad.datasource.entity.RecipeEntity

class RecipeCreateIntaractor
    (val imageDataSource: ImageDataSource, val recipeDataSource: RecipeDataSource) :
    RecipeCreateContract.Interactor {

    override fun uploadRecipe(
        recipe: RecipeCreateContract.Recipe,
        onSuccess: () -> Unit,
        onFailed: (Throwable) -> Unit
    ) {
        imageDataSource.saveImage(
            recipe.imageUri,
            onSuccess = { imagePath ->
                //　画像の保存に成功したらレシピをアップロード
                val entity = recipe.translate(imagePath)
                recipeDataSource.addRecipe(
                    entity,
                    onSuccess = onSuccess,
                    onFailed = onFailed
                )
            },
            onFailed = onFailed
        )
    }

    private fun RecipeCreateContract.Recipe.translate(imagePath: String): RecipeEntity =
        RecipeEntity(
            title = this.title,
            imagePath = imagePath,
            steps = this.steps,
            authorName = "クックパド美"
        )
}