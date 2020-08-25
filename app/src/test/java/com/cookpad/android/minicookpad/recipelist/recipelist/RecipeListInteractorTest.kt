package com.cookpad.android.minicookpad.recipelist.recipelist

import com.cookpad.android.minicookpad.datasource.RecipeDataSource
import com.cookpad.android.minicookpad.datasource.entity.RecipeEntity
import com.cookpad.android.minicookpad.recipelist.RecipeListContract
import com.cookpad.android.minicookpad.recipelist.RecipeListInteractor
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test

class RecipeListInteractorTest {
    lateinit var recipeDataSource: RecipeDataSource

    lateinit var interactor: RecipeListInteractor

    @Before
    fun setup() {
        recipeDataSource = mock() // モック作成
        interactor =
            RecipeListInteractor(
                recipeDataSource
            )
    }

    @Test
    fun verifyFetchRecipeListSuccess() {
        // given
        val onSuccess: (List<RecipeListContract.Recipe>) -> Unit = mock()
        val recipeList = listOf(
            RecipeEntity(
                id = "xxxx",
                title = "美味しいきゅうりの塩もみ",
                imagePath = "images/recipe.png",
                steps = listOf("きゅうりを切る", "塩をまく", "もむ"),
                authorName = "クックパド美"
            )
        )

        whenever(recipeDataSource.fetchAll(any(), any())).then {
            (it.arguments[0] as (List<RecipeEntity>) -> Unit).invoke(recipeList)
        }

        // when
        interactor.fetchRecipeList(onSuccess, {})

        // then
        val argumentCaptor = argumentCaptor<List<RecipeListContract.Recipe>>()
        verify(onSuccess).invoke(argumentCaptor.capture())
        argumentCaptor.firstValue.first().also {
            assertThat(it.id).isEqualTo("xxxx")
            assertThat(it.title).isEqualTo("美味しいきゅうりの塩もみ")
            assertThat(it.imagePath).isEqualTo("images/recipe.png")
            assertThat(it.steps).isEqualTo("きゅうりを切る、塩をまく、もむ")
            assertThat(it.authorName).isEqualTo("クックパド美")
        }
    }

    @Test
    fun verifyFetchRecipeListError() {
        // given
        val onFailed: (Throwable) -> Unit = mock()
        val exception = Exception("fetch list failed")
        whenever(recipeDataSource.fetchAll(any(), any())).then {
            (it.arguments[1] as (Throwable) -> Unit).invoke(exception)
        }

        // when
        interactor.fetchRecipeList({}, onFailed)

        // then
        val argumentCaptor = argumentCaptor<Exception>()
        verify(onFailed).invoke(argumentCaptor.capture())
        assertThat(argumentCaptor.firstValue.message).isEqualTo("fetch list failed")
    }
}

