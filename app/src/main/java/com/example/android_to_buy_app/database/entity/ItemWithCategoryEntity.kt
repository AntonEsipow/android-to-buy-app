package com.example.android_to_buy_app.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ItemWithCategoryEntity(

    @Embedded
    val itemEntity: ItemEntity,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val categoryEntity: CategoryEntity?
)
