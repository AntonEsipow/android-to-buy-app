package com.example.android_to_buy_app.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.android_to_buy_app.database.entity.ItemEntity

interface ItemEntityDao {
    @Query("SELECT * FROM item_entity")
    fun getAllItemEntity(): List<ItemEntity>

    @Insert
    fun insert(vararg ItemEntity: ItemEntity)

    @Delete
    fun delete(ItemEntity: ItemEntity)
}