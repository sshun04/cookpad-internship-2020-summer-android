package com.cookpad.android.minicookpad.recipecreate

import android.app.Activity

class RecipeCreateRouting(val activity: Activity) : RecipeCreateContract.Routing {
    override fun navigateRecipeList() {
        activity.finish()
    }
}