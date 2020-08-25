package com.cookpad.android.minicookpad.recipecreate

class RecipeCreatePresenter(
    val view: RecipeCreateContract.View,
    val interactor: RecipeCreateContract.Interactor,
    val routing: RecipeCreateContract.Routing
) : RecipeCreateContract.Presenter {
    override fun onSaveRequested(recipe: RecipeCreateContract.Recipe) {
        interactor.uploadRecipe(
            recipe,
            onSuccess = {
                view.renderSuccess()
                routing.navigateRecipeList()
            },
            onFailed = view::renderError
        )
    }
}