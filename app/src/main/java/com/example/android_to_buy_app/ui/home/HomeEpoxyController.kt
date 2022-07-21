package com.example.android_to_buy_app.ui.home

import android.content.res.ColorStateList
import android.text.TextUtils.isEmpty
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.dmp.tobuy.ui.epoxy.ViewBindingKotlinModel
import com.example.android_to_buy_app.R
import com.example.android_to_buy_app.SharedPrefUtil
import com.example.android_to_buy_app.addHeaderModel
import com.example.android_to_buy_app.arch.ToBuyViewModel
import com.example.android_to_buy_app.database.entity.ItemEntity
import com.example.android_to_buy_app.database.entity.ItemWithCategoryEntity
import com.example.android_to_buy_app.databinding.ModelItemEntityBinding
import com.example.android_to_buy_app.ui.epoxy.models.EmptyStateEpoxyModel
import com.example.android_to_buy_app.ui.epoxy.models.HeaderEpoxyModel
import com.example.android_to_buy_app.ui.epoxy.models.LoadingEpoxyModel

class HomeEpoxyController(
    private val itemEntityInterface: ItemEntityInterface
): EpoxyController() {

    var viewState: ToBuyViewModel.HomeViewState = ToBuyViewModel.HomeViewState(isLoading = true)
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {

        if(viewState.isLoading) {
            LoadingEpoxyModel().id("loading_state").addTo(this)
            return
        }

        if(viewState.dataList.isEmpty()) {
            EmptyStateEpoxyModel().id("empty_state").addTo(this)
            return
        }

        viewState.dataList.forEach { dataitem ->
            if(dataitem.isHeader) {
                addHeaderModel(dataitem.data as String)
                return@forEach
            }

            val itemWithCategoryEntity = dataitem.data as ItemWithCategoryEntity
            ItemEntityEpoxyModel(itemWithCategoryEntity, itemEntityInterface)
                .id(itemWithCategoryEntity.itemEntity.id)
                .addTo(this)
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

            val color = when(itemEntity.itemEntity.priority) {
                1 -> SharedPrefUtil.getLowPriorityColor()
                2 -> SharedPrefUtil.getMediumPriorityColor()
                3 -> SharedPrefUtil.getHighPriorityColor()
                else -> R.color.gray_700
            }

            priorityTextView.setBackgroundColor(color)
            root.setStrokeColor(ColorStateList.valueOf(color))

            root.setOnClickListener {
                itemEntityInterface.onItemSelected(itemEntity.itemEntity)
            }
            categoryNameTextView.text = itemEntity.categoryEntity?.name
        }
    }
}