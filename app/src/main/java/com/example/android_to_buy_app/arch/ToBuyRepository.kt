package com.example.android_to_buy_app.arch

import com.example.android_to_buy_app.database.AppDatabase
import com.example.android_to_buy_app.database.entity.ItemEntity

class ToBuyRepository(
    private val appDatabase: AppDatabase
) {

    suspend fun insertItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().insert(itemEntity)
    }

    suspend fun deleteItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().delete(itemEntity)
    }

    suspend fun getAllItems(): List<ItemEntity> {
        return appDatabase.itemEntityDao().getAllItemEntity()
    }
}