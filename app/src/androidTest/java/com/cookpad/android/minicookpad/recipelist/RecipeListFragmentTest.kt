package com.cookpad.android.minicookpad.recipelist

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cookpad.android.minicookpad.R
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class RecipeListFragmentTest {
    lateinit var presenter: RecipeListContract.Presenter

    @Before
    fun setup() {
        presenter = mock()
    }

    @Test
    fun verifyRecipeDetailNavigation() {
        // given
        val fragmentScenario = launchFragmentInContainer(themeResId = R.style.AppTheme) {
            RecipeListFragment()
        }.moveToState(Lifecycle.State.RESUMED)
        sleep(5000)
        fragmentScenario.onFragment { it.presenter = presenter }
    }
}