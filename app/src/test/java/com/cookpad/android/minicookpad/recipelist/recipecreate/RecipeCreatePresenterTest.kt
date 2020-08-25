package com.cookpad.android.minicookpad.recipelist.recipecreate

import com.cookpad.android.minicookpad.recipecreate.RecipeCreateContract
import com.cookpad.android.minicookpad.recipecreate.RecipeCreatePresenter
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test

class RecipeCreatePresenterTest {
    lateinit var interactor: RecipeCreateContract.Interactor
    lateinit var routing: RecipeCreateContract.Routing
    lateinit var view: RecipeCreateContract.View

    lateinit var presenter: RecipeCreatePresenter

    @Before
    fun setUp() {
        interactor = mock()
        routing = mock()
        view = mock()

        presenter = RecipeCreatePresenter(view, interactor, routing)
    }

    @Test
    fun verifyRecipeCreateSuccess() {
        // given
        val dammyRecipe = RecipeCreateContract.Recipe("dammy", "dammy_image", listOf())
        whenever(interactor.uploadRecipe(any(), any(), any())).then {
            (it.arguments[1] as (() -> Unit)).invoke()
        }

        //when
        presenter.onSaveRequested(dammyRecipe)

        //then
        val argumentCaptor = argumentCaptor<RecipeCreateContract.Recipe>()
        verify(interactor).uploadRecipe(argumentCaptor.capture(), any(), any())
        verify(view).renderSuccess()
        verify(routing).navigateRecipeList()
        argumentCaptor.firstValue.let {
            print(it)
        }
    }
}