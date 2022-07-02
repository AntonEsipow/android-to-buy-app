package com.example.android_to_buy_app.ui.home

import com.example.android_to_buy_app.database.entity.ItemEntity

interface ItemEntityInterface {
    fun onDeleteItemEntity(itemEntity: ItemEntity)
    fun onBumpPriority(itemEntity: ItemEntity)
}