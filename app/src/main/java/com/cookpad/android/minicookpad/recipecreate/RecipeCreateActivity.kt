package com.cookpad.android.minicookpad.recipecreate

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.cookpad.android.minicookpad.RecipeCreateViewModel
import com.cookpad.android.minicookpad.databinding.ActivityRecipeCreateBinding
import com.cookpad.android.minicookpad.datasource.impl.FirebaseImageDataSource
import com.cookpad.android.minicookpad.datasource.impl.FirebaseRecipeDataSource
import com.google.android.material.snackbar.Snackbar

class RecipeCreateActivity : AppCompatActivity(), RecipeCreateContract.View {
    private lateinit var binding: ActivityRecipeCreateBinding

    private val viewModel: RecipeCreateViewModel by viewModels()

    private val launcher: ActivityResultLauncher<Unit> by lazy {
        registerForActivityResult(ImageSelector()) { imageUri ->
            imageUri?.let {
                viewModel.updateImageUri(it.toString())
            }
        }
    }

    lateinit var presenter: RecipeCratePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = RecipeCratePresenter(
            this,
            RecipeCreateInteractor(FirebaseImageDataSource(), FirebaseRecipeDataSource()),
            RecipeCreateRouting(this)
        )

        supportActionBar?.apply {
            title = "レシピ作成"
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        binding.image.setOnClickListener {
            launcher.launch(null)
        }

        viewModel.imageUri.observe(this, Observer { imageUri ->
            Glide.with(this)
                .load(imageUri)
                .into(binding.image)
        })

        binding.saveButton.setOnClickListener {
            val entity = RecipeCreateContract.Recipe(
                binding.title.text?.toString()?: "",
                viewModel.requireImageUri(),
                listOf(
                    binding.step1.text?.toString() ?: "",
                    binding.step2.text?.toString() ?: "",
                    binding.step3.text?.toString() ?: ""
                )
            )

            presenter.onSaveRequested(entity)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun renderSuccess() {

    }

    override fun renderError(exception: Throwable) {
        TODO("Not yet implemented")
    }

    class ImageSelector : ActivityResultContract<Unit, Uri?>() {
        override fun createIntent(context: Context, input: Unit?): Intent =
            Intent.createChooser(
                Intent().apply {
                    type = "image/*"
                    action = Intent.ACTION_GET_CONTENT
                },
                "レシピ写真"
            )

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? = intent?.data
    }
}