package com.example.android_to_buy_app.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_to_buy_app.database.entity.CategoryEntity
import com.example.android_to_buy_app.databinding.FragmentAddCategoryBinding
import com.example.android_to_buy_app.ui.BaseFragment
import java.util.*

class AddCategoryFragment: BaseFragment() {

    private var _binding: FragmentAddCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoryNameEditText.requestFocus()
        binding.saveButton.setOnClickListener {
            saveCategoryToDatabase()
        }

        sharedViewModel.transactionCompletedLiveData.observe(viewLifecycleOwner) { completed ->
            if (completed) {
                navigateUp()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        sharedViewModel.transactionCompletedLiveData.postValue(false)
    }

    private fun saveCategoryToDatabase() {
        val categoryName = binding.categoryNameEditText.text.toString().trim()
        if (categoryName.isEmpty()) {
            binding.categoryNameTextField.error = "* Required field"
            return
        }

        val categoryEntity = CategoryEntity(
            id = UUID.randomUUID().toString(),
            name = categoryName
        )

        sharedViewModel.insertCategory(categoryEntity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}