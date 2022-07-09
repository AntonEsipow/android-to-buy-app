package com.example.android_to_buy_app.ui.home

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.dmp.tobuy.ui.epoxy.ViewBindingKotlinModel
import com.example.android_to_buy_app.R
import com.example.android_to_buy_app.database.entity.ItemEntity
import com.example.android_to_buy_app.databinding.ModelItemEntityBinding
import com.example.android_to_buy_app.ui.epoxy.EmptyStateEpoxyModel
import com.example.android_to_buy_app.ui.epoxy.HeaderEpoxyModel
import com.example.android_to_buy_app.ui.epoxy.LoadingEpoxyModel

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

    var itemEntityList = ArrayList<ItemEntity>()
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

        if(itemEntityList.isEmpty()) {
            EmptyStateEpoxyModel().id("empty_state").addTo(this)
            return
        }

        var currentPriority: Int = -1
        itemEntityList.sortByDescending { it.priority }
        itemEntityList.forEach { item ->
            if (item.priority != currentPriority) {
                currentPriority = item.priority
                val text = getHeaderTextForPriority(currentPriority)
                val color = getColorFromPriority(currentPriority)
                HeaderEpoxyModel(text, color).id(text).addTo(this)
            }
            ItemEntityEpoxyModel(item, itemEntityInterface).id(item.id).addTo(this)
        }
    }

    private fun getHeaderTextForPriority(currentPriority: Int): String {
        return when(currentPriority) {
            1 -> "Low"
            2 -> "Medium"
            else -> "High"
        }
    }

    private fun getColorFromPriority(priority: Int): Int {
        return when(priority) {
            1 -> R.color.model_header_background_low
            2 -> R.color.model_header_background_medium
            3 -> R.color.model_header_background_high
            else -> R.color.purple_700
        }
    }

    data class ItemEntityEpoxyModel(
        val itemEntity: ItemEntity,
        val itemEntityInterface: ItemEntityInterface
    ): ViewBindingKotlinModel<ModelItemEntityBinding>(R.layout.model_item_entity) {



        override fun ModelItemEntityBinding.bind() {
            titleTextView.text = itemEntity.title

            if (itemEntity.description == null) {
                descriptionTextView.isGone = true
            } else {
                descriptionTextView.isVisible = true
                descriptionTextView.text = itemEntity.description
            }

            priorityTextView.setOnClickListener {
                itemEntityInterface.onBumpPriority(itemEntity)
            }

            val colorRes = when(itemEntity.priority) {
                1 -> android.R.color.holo_green_dark
                2 -> android.R.color.holo_orange_dark
                3 -> android.R.color.holo_red_dark
                else -> R.color.purple_700
            }

            val color = ContextCompat.getColor(root.context, colorRes)
            priorityTextView.setBackgroundColor(color)
            root.setStrokeColor(ColorStateList.valueOf(color))
        }
    }
}