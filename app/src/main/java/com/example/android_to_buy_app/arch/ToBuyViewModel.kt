package com.example.android_to_buy_app.arch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_to_buy_app.database.AppDatabase
import com.example.android_to_buy_app.database.entity.ItemEntity
import kotlinx.coroutines.launch
import java.lang.Appendable

class ToBuyViewModel: ViewModel() {

    private lateinit var repository: ToBuyRepository

    val itemEntitiesLiveData = MutableLiveData<List<ItemEntity>>()

    fun init(appDatabase: AppDatabase) {
        repository = ToBuyRepository(appDatabase)

        viewModelScope.launch {
            val items = repository.getAllItems()
            itemEntitiesLiveData.postValue(items)
        }
    }

    fun insertItem(itemEntity: ItemEntity) {
        repository.insertItem(itemEntity)
    }

    fun deleteItem(itemEntity: ItemEntity) {
        repository.deleteItem(itemEntity)
    }
}