package com.example.android_to_buy_app.ui.customization

import com.example.android_to_buy_app.database.entity.CategoryEntity

interface CategoryEntityInterface {

    fun onCategoryEmptyStateClicked()
    fun onDeleteCategory(categoryEntity: CategoryEntity)
    fun onCategorySelected(categoryEntity: CategoryEntity)
}