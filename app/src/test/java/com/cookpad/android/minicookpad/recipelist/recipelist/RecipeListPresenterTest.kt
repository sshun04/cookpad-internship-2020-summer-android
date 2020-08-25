package com.cookpad.android.minicookpad.recipelist.recipelist

import com.cookpad.android.minicookpad.recipelist.RecipeListContract
import com.cookpad.android.minicookpad.recipelist.RecipeListPresenter
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test

class RecipeListPresenterTest {
    lateinit var interactor: RecipeListContract.Interactor
    lateinit var routing: RecipeListContract.Routing
    lateinit var view: RecipeListContract.View

    // Test対象のクラス
    lateinit var presenter: RecipeListPresenter

    @Before
    fun setup() {
        interactor = mock()
        routing = mock()
        view = mock()

        presenter =
            RecipeListPresenter(
                view,
                interactor,
                routing
            )
    }


    @Test
    fun verifyRequestRecipeListSuccess() {
        // given
        val mockList = emptyList<RecipeListContract.Recipe>()
        whenever(interactor.fetchRecipeList(any(), any())).then {
            (it.arguments[0] as (List<RecipeListContract.Recipe>) -> Unit).invoke(mockList)
        }

        // when
        presenter.onRecipeListRequested()

        // then
        verify(interactor).fetchRecipeList(any(), any())
        verify(view).renderRecipeList(mockList)
    }

    @Test
    fun verifyRequestRecipeListFail() {

        val mockException = Exception()
        // given
        whenever(interactor.fetchRecipeList(any(), any())).then {
            (it.arguments[1] as (Exception) -> Unit).invoke(mockException)
        }

        // when
        presenter.onRecipeListRequested()

        // then
        verify(interactor).fetchRecipeList(any(), any())
        verify(view).renderError(mockException)
    }

    @Test
    fun onRecipeDetailRequested() {
        // given
        val recipeId = "recipeId"
        val recipeName = "recipeName"

        // when
        presenter.onRecipeDetailRequested(recipeId, recipeName)

        // then
        verify(routing).navigateRecipeDetail(recipeId,recipeName)
    }
}