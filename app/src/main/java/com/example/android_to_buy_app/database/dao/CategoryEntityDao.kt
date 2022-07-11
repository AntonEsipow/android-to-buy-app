package com.example.android_to_buy_app.database.dao

import androidx.room.*
import com.example.android_to_buy_app.database.entity.CategoryEntity
import com.example.android_to_buy_app.database.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryEntityDao {

    @Query("SELECT * FROM category_entity")
    fun getAllItemEntity(): Flow<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg categoryEntity: CategoryEntity)

    @Delete
    suspend fun delete(categoryEntity: CategoryEntity)

    @Update
    suspend fun update(categoryEntity: CategoryEntity)
}