package com.example.android_to_buy_app.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.android_to_buy_app.database.entity.ItemEntity

@Dao
interface ItemEntityDao {

    @Query("SELECT * FROM item_entity")
     suspend fun getAllItemEntity(): List<ItemEntity>

    @Insert
    suspend fun insert(vararg ItemEntity: ItemEntity)

    @Delete
    suspend fun delete(ItemEntity: ItemEntity)
}