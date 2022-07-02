package com.example.android_to_buy_app.arch

import com.example.android_to_buy_app.database.AppDatabase
import com.example.android_to_buy_app.database.entity.ItemEntity

class ToBuyRepository(
    private val appDatabase: AppDatabase
) {

    fun insertItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().insert(itemEntity)
    }

    fun deleteItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().delete(itemEntity)
    }

    suspend fun getAllItems(): List<ItemEntity> {
        return appDatabase.itemEntityDao().getAllItemEntity()
    }
}