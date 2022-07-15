package com.example.android_to_buy_app.database.dao

import androidx.room.*
import com.example.android_to_buy_app.database.entity.ItemEntity
import com.example.android_to_buy_app.database.entity.ItemWithCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemEntityDao {

    @Query("SELECT * FROM item_entity")
    fun getAllItemEntity(): Flow<List<ItemEntity>>

    @Transaction
    @Query("SELECT * FROM item_entity")
    fun getAllItemWithCategoryEntities(): Flow<List<ItemWithCategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg ItemEntity: ItemEntity)

    @Delete
    suspend fun delete(ItemEntity: ItemEntity)

    @Update
    suspend fun update(ItemEntity: ItemEntity)
}