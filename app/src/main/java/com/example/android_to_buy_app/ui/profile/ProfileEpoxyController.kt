package com.example.android_to_buy_app.ui.profile

import com.airbnb.epoxy.EpoxyController
import com.dmp.tobuy.ui.epoxy.ViewBindingKotlinModel
import com.example.android_to_buy_app.R
import com.example.android_to_buy_app.addHeaderModel
import com.example.android_to_buy_app.database.entity.CategoryEntity
import com.example.android_to_buy_app.databinding.FragmentAddCategoryBinding
import com.example.android_to_buy_app.databinding.ModelCategoryBinding
import com.example.android_to_buy_app.databinding.ModelEmptyButtonBinding

class ProfileEpoxyController(
    private val onCategoryEmptyStateClicked: () -> Unit
): EpoxyController() {

    var categories : List<CategoryEntity> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {

        // Categories section
        addHeaderModel("Categories")

        categories.forEach {
            CategoryEpoxyModel(it).id(it.id).addTo(this)
        }

        EmptyButtonEpoxyModel("Add category", onCategoryEmptyStateClicked)
            .id("add_Category")
            .addTo(this)
    }

    data class CategoryEpoxyModel(
        val categoryEntity: CategoryEntity
    ): ViewBindingKotlinModel<ModelCategoryBinding>(R.layout.model_category) {

        override fun ModelCategoryBinding.bind() {
            textView.text = categoryEntity.name
        }
    }

    data class EmptyButtonEpoxyModel(
        val buttonText: String,
        val onClicked: () -> Unit
    ) : ViewBindingKotlinModel<ModelEmptyButtonBinding>(R.layout.model_empty_button) {

        override fun ModelEmptyButtonBinding.bind() {
            button.text = buttonText
            button.setOnClickListener { onClicked.invoke() }
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }
}