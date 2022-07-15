package com.example.android_to_buy_app.ui.home.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.android_to_buy_app.R
import com.example.android_to_buy_app.database.entity.CategoryEntity
import com.example.android_to_buy_app.database.entity.ItemEntity
import com.example.android_to_buy_app.databinding.FragmentAddItemEntityBinding
import com.example.android_to_buy_app.ui.BaseFragment
import java.util.*

class AddItemEntityFragment: BaseFragment() {

    private var _binding: FragmentAddItemEntityBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: AddItemEntityFragmentArgs by navArgs()
    private val selectedItemEntity: ItemEntity? by lazy {
        sharedViewModel.itemEntitiesLiveData.value?.find {
            it.id == safeArgs.selectedItemEntityId
        }
    }
    private var isInEditMode: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddItemEntityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showKeyboard()
        binding.saveButton.setOnClickListener {
            saveItemEntityToTheDatabase()
        }

        sharedViewModel.transactionCompletedLiveData.observe(viewLifecycleOwner) { event ->
            event.getContent()?.let {
                if (isInEditMode) {
                    navigateUp()
                    return@observe
                }
                refreshAddItemEntityScreen()
            }
        }
        binding.titleEditText.requestFocus()
        // Setup screen if we are in edit mode
        setupSelectedItemEntity()

        val categoryViewStateEpoxyController = CategoryViewStateEpoxyController { categoryId ->
            sharedViewModel.onCategorySelected(categoryId)
        }
        binding.categoriesEpoxyRecyclerView.setController(categoryViewStateEpoxyController)
        sharedViewModel.onCategorySelected(selectedItemEntity?.categoryId ?: CategoryEntity.DEFAULT_CATEGORY_ID, true)
        sharedViewModel.categoriesViewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            categoryViewStateEpoxyController.viewState = viewState
        }
    }

    private fun saveItemEntityToTheDatabase() {
        val itemTitle = binding.titleEditText.text.toString().trim()
        if(itemTitle.isEmpty()) {
            binding.titleEditText.error = "* Required field"
            return
        }
        binding.titleEditText.error = null

        var itemDescription: String? = binding.descriptionEditText.text.toString().trim()
        if (itemDescription?.isEmpty() == true) {
            itemDescription = null
        }
        val itemPriority = when(binding.radioGroup.checkedRadioButtonId) {
            R.id.radioButtonLow -> 1
            R.id.radioButtonMedium -> 2
            R.id.radioButtonHigh -> 3
            else -> 0
        }
        val itemCategoryId = sharedViewModel.categoriesViewStateLiveData.value?.getSelectedCategoryId() ?: return

        if(isInEditMode) {
            val itemEntity = selectedItemEntity!!.copy(
                title = itemTitle,
                description = itemDescription,
                priority = itemPriority,
                categoryId = itemCategoryId
            )
            sharedViewModel.updateItem(itemEntity)
            return
        }

        val itemEntity = ItemEntity(
            id = UUID.randomUUID().toString(),
            title = itemTitle,
            description = itemDescription,
            priority = itemPriority,
            createdAt = System.currentTimeMillis(),
            categoryId = itemCategoryId
        )

        sharedViewModel.insertItem(itemEntity)
    }

    private fun refreshAddItemEntityScreen() {
        binding.titleEditText.text = null
        binding.titleEditText.requestFocus()
        binding.descriptionEditText.text = null
        binding.radioGroup.check(R.id.radioButtonLow)
        Toast.makeText(requireActivity(), "Note successfully added!", Toast.LENGTH_SHORT).show()
    }

    private fun setupSelectedItemEntity() {
        selectedItemEntity?.let { itemEntity ->
            isInEditMode = true

            binding.titleEditText.setText(itemEntity.title)
            binding.titleEditText.setSelection(itemEntity.title.length)
            binding.descriptionEditText.setText(itemEntity.description)
            binding.radioGroup.check(
                when(itemEntity.priority) {
                    1 -> R.id.radioButtonLow
                    2 -> R.id.radioButtonMedium
                    else -> R.id.radioButtonHigh
                }
            )
            binding.saveButton.text = "Update"
            mainActivity.supportActionBar?.title = "Update item"
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}