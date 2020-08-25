package com.cookpad.android.minicookpad.recipelist.recipecreate

import com.cookpad.android.minicookpad.datasource.ImageDataSource
import com.cookpad.android.minicookpad.datasource.RecipeDataSource
import com.cookpad.android.minicookpad.recipecreate.RecipeCreateContract
import com.cookpad.android.minicookpad.recipecreate.RecipeCreateIntaractor
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test

class RecipeCreateIntaractorTest {
    lateinit var recipeDataSource: RecipeDataSource
    lateinit var imageDataSource: ImageDataSource

    lateinit var intaractor: RecipeCreateIntaractor

    @Before
    fun setup() {
        recipeDataSource = mock()
        imageDataSource = mock()

        intaractor = RecipeCreateIntaractor(imageDataSource, recipeDataSource)
    }

    // イメージのアップロードとレシピのアップロードが成功した際にonSuccessが呼ばれるかのテスト
    @Test
    fun verifyUpLoadRecipeSuccess() {
        // given
        val onSuccess: () -> Unit = mock()
        val dammyRecipe: RecipeCreateContract.Recipe = RecipeCreateContract.Recipe("", "", listOf())
        whenever(imageDataSource.saveImage(any(), any(), any())).then {
            (it.arguments[1] as (String) -> Unit).invoke("")
        }
        whenever(recipeDataSource.addRecipe(any(), any(), any())).then {
            (it.arguments[1] as () -> Unit).invoke()
        }

        //when
        intaractor.uploadRecipe(dammyRecipe, onSuccess, {})

        //then
        verify(onSuccess).invoke()
    }
}