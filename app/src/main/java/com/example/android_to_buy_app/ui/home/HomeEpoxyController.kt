package com.example.android_to_buy_app.ui.home

import android.content.res.ColorStateList
import android.text.TextUtils.isEmpty
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.dmp.tobuy.ui.epoxy.ViewBindingKotlinModel
import com.example.android_to_buy_app.R
import com.example.android_to_buy_app.addHeaderModel
import com.example.android_to_buy_app.database.entity.ItemEntity
import com.example.android_to_buy_app.database.entity.ItemWithCategoryEntity
import com.example.android_to_buy_app.databinding.ModelItemEntityBinding
import com.example.android_to_buy_app.ui.epoxy.models.EmptyStateEpoxyModel
import com.example.android_to_buy_app.ui.epoxy.models.HeaderEpoxyModel
import com.example.android_to_buy_app.ui.epoxy.models.LoadingEpoxyModel

class HomeEpoxyController(
    private val itemEntityInterface: ItemEntityInterface
): EpoxyController() {

    var isLoading: Boolean = true
        set(value) {
            field = value
            if(field) {
                requestModelBuild()
            }
        }

    var items: List<ItemWithCategoryEntity> = emptyList()
        set(value) {
            field = value
            isLoading = false
            requestModelBuild()
        }

    override fun buildModels() {

        if(isLoading) {
            LoadingEpoxyModel().id("loading_state").addTo(this)
            return
        }

        if(items.isEmpty()) {
            EmptyStateEpoxyModel().id("empty_state").addTo(this)
            return
        }

        var currentPriority: Int = -1
        items.sortedByDescending { it.itemEntity.priority }
            .forEach { item ->
            if (item.itemEntity.priority != currentPriority) {
                currentPriority = item.itemEntity.priority
                addHeaderModel(getHeaderTextForPriority(currentPriority))
            }
            ItemEntityEpoxyModel(item, itemEntityInterface)
                .id(item.itemEntity.id)
                .addTo(this)
        }
    }

    private fun getHeaderTextForPriority(currentPriority: Int): String {
        return when(currentPriority) {
            1 -> "Low"
            2 -> "Medium"
            else -> "High"
        }
    }

    data class ItemEntityEpoxyModel(
        val itemEntity: ItemWithCategoryEntity,
        val itemEntityInterface: ItemEntityInterface
    ): ViewBindingKotlinModel<ModelItemEntityBinding>(R.layout.model_item_entity) {

        override fun ModelItemEntityBinding.bind() {
            titleTextView.text = itemEntity.itemEntity.title

            if (itemEntity.itemEntity.description == null) {
                descriptionTextView.isGone = true
            } else {
                descriptionTextView.isVisible = true
                descriptionTextView.text = itemEntity.itemEntity.description
            }

            priorityTextView.setOnClickListener {
                itemEntityInterface.onBumpPriority(itemEntity.itemEntity)
            }

            val colorRes = when(itemEntity.itemEntity.priority) {
                1 -> android.R.color.holo_green_dark
                2 -> android.R.color.holo_orange_dark
                3 -> android.R.color.holo_red_dark
                else -> R.color.gray_700
            }

            val color = ContextCompat.getColor(root.context, colorRes)
            priorityTextView.setBackgroundColor(color)
            root.setStrokeColor(ColorStateList.valueOf(color))

            root.setOnClickListener {
                itemEntityInterface.onItemSelected(itemEntity.itemEntity)
            }
            categoryNameTextView.text = itemEntity.categoryEntity?.name
        }
    }
}