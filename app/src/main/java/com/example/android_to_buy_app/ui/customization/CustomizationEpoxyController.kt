package com.example.android_to_buy_app.ui.customization

import android.app.AlertDialog
import android.content.res.ColorStateList
import com.airbnb.epoxy.EpoxyController
import com.dmp.tobuy.ui.epoxy.ViewBindingKotlinModel
import com.example.android_to_buy_app.R
import com.example.android_to_buy_app.SharedPrefUtil
import com.example.android_to_buy_app.addHeaderModel
import com.example.android_to_buy_app.database.entity.CategoryEntity
import com.example.android_to_buy_app.databinding.ModelCategoryBinding
import com.example.android_to_buy_app.databinding.ModelEmptyButtonBinding
import com.example.android_to_buy_app.databinding.ModelPriorityColorItemBinding

class CustomizationEpoxyController(
    private val customizationInterface: CustomizationInterface
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
            CategoryEpoxyModel(it, customizationInterface).id(it.id).addTo(this)
        }

        EmptyButtonEpoxyModel("Add category",customizationInterface)
            .id("add_Category")
            .addTo(this)

        // Priority customization section
        addHeaderModel("Priorities")

        val highPriorityColor = SharedPrefUtil.getHighPriorityColor()
        val mediumPriorityColor = SharedPrefUtil.getMediumPriorityColor()
        val lowPriorityColor = SharedPrefUtil.getLowPriorityColor()

        PriorityColorItemEpoxyModel("High", highPriorityColor, customizationInterface)
            .id("priority_high")
            .addTo(this)
        PriorityColorItemEpoxyModel("Medium", mediumPriorityColor, customizationInterface)
            .id("priority_medium")
            .addTo(this)
        PriorityColorItemEpoxyModel("Low", lowPriorityColor, customizationInterface)
            .id("priority_low")
            .addTo(this)
    }

    // region Epoxy Model
    data class CategoryEpoxyModel(
        val categoryEntity: CategoryEntity,
        val customizationInterface: CustomizationInterface
    ): ViewBindingKotlinModel<ModelCategoryBinding>(R.layout.model_category) {

        override fun ModelCategoryBinding.bind() {
            textView.text = categoryEntity.name

            root.setOnClickListener {
                customizationInterface.onCategorySelected(categoryEntity)
            }

            root.setOnLongClickListener {
                AlertDialog.Builder(it.context)
                    .setTitle("Delete ${categoryEntity.name}?")
                    .setPositiveButton("Yes") {_,_ ->
                        customizationInterface.onDeleteCategory(categoryEntity)
                    }
                    .setNegativeButton("Cancel") {_,_ ->
                    }
                    .show()
                return@setOnLongClickListener true
            }
        }
    }

    data class EmptyButtonEpoxyModel(
        val buttonText: String,
        val categoryEntityInterface: CustomizationInterface
    ) : ViewBindingKotlinModel<ModelEmptyButtonBinding>(R.layout.model_empty_button) {

        override fun ModelEmptyButtonBinding.bind() {
            button.text = buttonText
            button.setOnClickListener {
                categoryEntityInterface.onCategoryEmptyStateClicked()
            }
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }

    data class PriorityColorItemEpoxyModel(
        val displayText: String,
        val displayColor: Int,
        val customizationInterface: CustomizationInterface
    ) : ViewBindingKotlinModel<ModelPriorityColorItemBinding>(R.layout.model_priority_color_item) {

        override fun ModelPriorityColorItemBinding.bind() {
            textView.text = displayText
            root.setStrokeColor(ColorStateList.valueOf(displayColor))
            imageView.setBackgroundColor(displayColor)
            imageView.setOnClickListener { customizationInterface.onPrioritySelected(displayText) }
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }
    // endregion Epoxy Model
}