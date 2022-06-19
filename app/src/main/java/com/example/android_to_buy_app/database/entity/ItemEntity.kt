package com.example.android_to_buy_app.database.entity

import androidx.room.Entity

@Entity(tableName = "item_entity")
data class ItemEntity (
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val priority: Int = 0,
    val createdAt: Long = 0L,
    val categoryId: String = ""
)