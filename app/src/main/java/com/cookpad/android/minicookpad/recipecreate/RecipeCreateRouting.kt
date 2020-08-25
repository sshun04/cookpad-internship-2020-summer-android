package com.cookpad.android.minicookpad.recipecreate

class RecipeCreateRouting(val activity: RecipeCreateActivity) : RecipeCreateContract.Routing {
    override fun navigateRecipeList() {
        activity.finish()
    }
}