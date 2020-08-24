package com.cookpad.android.minicookpad

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cookpad.android.minicookpad.databinding.ListitemRecipeBinding
import com.cookpad.android.minicookpad.recipelist.RecipeListContract
import com.google.firebase.storage.FirebaseStorage

typealias OnRecipeClickListener = (String, String) -> Unit

class RecipeListAdapter(
    private val onRecipeClickListener: OnRecipeClickListener
) : RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>() {

    private var recipeEntityList: List<RecipeListContract.Recipe> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding =
            ListitemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipeEntityList[position], onRecipeClickListener)
    }

    override fun getItemCount(): Int = recipeEntityList.size

    override fun getItemViewType(position: Int): Int =
        R.layout.listitem_recipe

    fun update(recipeEntityList: List<RecipeListContract.Recipe>) {
        DiffUtil
            .calculateDiff(
                RecipeDiffCallback(
                    this.recipeEntityList,
                    recipeEntityList
                )
            )
            .dispatchUpdatesTo(this)
        this.recipeEntityList = recipeEntityList
    }

    class RecipeViewHolder(
        private val binding: ListitemRecipeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        fun bind(
            recipeEntity: RecipeListContract.Recipe,
            onRecipeClickListener: OnRecipeClickListener
        ) {
            binding.root.setOnClickListener {
                recipeEntity.id?.let { id -> onRecipeClickListener.invoke(id, recipeEntity.title) }
            }
            binding.title.text = recipeEntity.title
            binding.authorName.text = "by ${recipeEntity.authorName}"
            recipeEntity.imagePath?.let { path ->
                Glide.with(context)
                    .load(FirebaseStorage.getInstance().reference.child(path))
                    .into(binding.image)
            }
            binding.steps.text = recipeEntity.steps
        }
    }

    class RecipeDiffCallback(
        private val oldList: List<RecipeListContract.Recipe>,
        private val newList: List<RecipeListContract.Recipe>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].javaClass == newList[newItemPosition].javaClass

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id
    }
}