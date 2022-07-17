package com.example.android_to_buy_app.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_to_buy_app.database.AppDatabase
import com.example.android_to_buy_app.database.entity.CategoryEntity
import com.example.android_to_buy_app.database.entity.ItemEntity
import com.example.android_to_buy_app.database.entity.ItemWithCategoryEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Appendable

class ToBuyViewModel: ViewModel() {

    private lateinit var repository: ToBuyRepository

    val itemEntitiesLiveData = MutableLiveData<List<ItemEntity>>()
    val categoryEntitiesLiveData = MutableLiveData<List<CategoryEntity>>()
    private val itemWithCategoryEntitiesLiveData = MutableLiveData<List<ItemWithCategoryEntity>>()

    // Home page
    private val dataList = ArrayList<HomeViewState.DataItem<*>>()
    private val currentSort: HomeViewState.Sort = HomeViewState.Sort.NONE
    private val _homeViewStateLiveData = MutableLiveData<HomeViewState>()
    val homeViewStateLiveData: LiveData<HomeViewState>
        get() = _homeViewStateLiveData

    // Categories in the Add/Update screen
    private val _categoriesViewStateLiveData = MutableLiveData<CategoriesViewState>()
    val categoriesViewStateLiveData: LiveData<CategoriesViewState>
        get() = _categoriesViewStateLiveData

    val transactionCompletedLiveData = MutableLiveData<Event<Boolean>>()

    fun init(appDatabase: AppDatabase) {
        repository = ToBuyRepository(appDatabase)

        // Initialize our Flow connectivity to the DB
        viewModelScope.launch {
            repository.getAllItems().collect { items ->
                itemEntitiesLiveData.postValue(items)
            }
        }

        viewModelScope.launch {
            repository.getAllItemWithCategoryEntities().collect { items ->
                itemWithCategoryEntitiesLiveData.postValue(items)

                updateHomeViewState(items)
            }
        }

        viewModelScope.launch {
            repository.getAllCategories().collect { categories ->
                categoryEntitiesLiveData.postValue(categories)
            }
        }
    }

    private fun updateHomeViewState(items: List<ItemWithCategoryEntity>) {

        when(currentSort) {
            HomeViewState.Sort.NONE -> sortingByDefault(items)
            HomeViewState.Sort.CATEGORY -> {
                // todo implement me
            }
            HomeViewState.Sort.OLDEST -> {
                // todo implement me
            }
            HomeViewState.Sort.NEWEST -> {
                // todo implement me
            }
        }
    }

    data class HomeViewState(
        val dataList: List<DataItem<*>> = emptyList(),
        val isLoading: Boolean = false,
        val sort: Sort = Sort.NONE
    ) {
        data class DataItem<T>(
            val data: T,
            val isHeader: Boolean = false
        )

        enum class Sort(val displayName: String) {
            NONE("None"),
            CATEGORY("Category"),
            OLDEST("Oldest"),
            NEWEST("Newest")
        }
    }

    private fun getHeaderTextForPriority(priority: Int): String {
        return when (priority) {
            1 -> "Low"
            2 -> "Medium"
            else -> "High"
        }
    }

    private fun sortingByDefault(items: List<ItemWithCategoryEntity>) {
        var currentPriority: Int = -1
        items.sortedByDescending {
            it.itemEntity.priority
        }.forEach { item ->
            if (item.itemEntity.priority != currentPriority) {
                currentPriority = item.itemEntity.priority
                val headerItem = HomeViewState.DataItem(
                    data = getHeaderTextForPriority(currentPriority),
                    isHeader = true
                )

                dataList.add(headerItem)
            }

            val dataItem = HomeViewState.DataItem(data = item)
            dataList.add(dataItem)
        }
    }

    // region Category
    fun onCategorySelected(categoryId: String, showLoading: Boolean = false) {
        if (showLoading) {
            val loadingViewState = CategoriesViewState(isLoading = true)
            _categoriesViewStateLiveData.value = loadingViewState
        }

        val categories = categoryEntitiesLiveData.value ?: return
        val viewStateItemList = ArrayList<CategoriesViewState.Item>()

        // Default category (un-selecting a category)
        viewStateItemList.add(CategoriesViewState.Item(
            categoryEntity = CategoryEntity.getDefaultCategory(),
            isSelected = categoryId == CategoryEntity.DEFAULT_CATEGORY_ID
        ))

        // Populate the rest of the list with what we have in the DB
        categories.forEach {
            viewStateItemList.add(CategoriesViewState.Item(
                categoryEntity = it,
                isSelected = it.id == categoryId
            ))
        }
        val viewState = CategoriesViewState(itemList = viewStateItemList)
        _categoriesViewStateLiveData.postValue(viewState)
    }

    data class CategoriesViewState(
        val isLoading: Boolean = false,
        val itemList: List<Item> = emptyList()
    ) {
        data class Item(
            val categoryEntity: CategoryEntity = CategoryEntity(),
            val isSelected: Boolean = false
        )

        fun getSelectedCategoryId(): String {
            return itemList.find { it.isSelected }?.categoryEntity?.id
                ?: CategoryEntity.DEFAULT_CATEGORY_ID
        }
    }
    // endregion Category

    // region ItemEntity
    fun insertItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.insertItem(itemEntity)

            transactionCompletedLiveData.postValue(Event(true))
        }
    }

    fun deleteItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.deleteItem(itemEntity)
        }
    }

    fun updateItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.updateItem(itemEntity)

            transactionCompletedLiveData.postValue(Event(true))
        }
    }
    // endregion ItemEntity

    // region CategoryEntity
    fun insertCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            repository.insertCategory(categoryEntity)

            transactionCompletedLiveData.postValue(Event(true))
        }
    }

    fun deleteCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            repository.deleteCategory(categoryEntity)
        }
    }

    fun updateCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            repository.updateCategory(categoryEntity)

            transactionCompletedLiveData.postValue(Event(true))
        }
    }
    // endregion CategoryEntity
}