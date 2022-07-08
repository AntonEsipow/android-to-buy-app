package com.example.android_to_buy_app.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android_to_buy_app.R
import com.example.android_to_buy_app.database.entity.ItemEntity
import com.example.android_to_buy_app.databinding.FragmentAddItemEntityBinding
import com.example.android_to_buy_app.ui.BaseFragment
import java.util.*

class AddItemEntityFragment: BaseFragment() {

    private var _binding: FragmentAddItemEntityBinding? = null
    private val binding get() = _binding!!

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

        binding.saveButton.setOnClickListener {
            saveItemEntityToTheDatabase()
        }

        sharedViewModel.transactionCompletedLiveData.observe(viewLifecycleOwner) { complete ->
            if (complete) {
                refreshAddItemEntityScreen()
            }
        }
        binding.titleEditText.requestFocus()
    }

    override fun onPause() {
        super.onPause()
        sharedViewModel.transactionCompletedLiveData.postValue(false)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun saveItemEntityToTheDatabase() {
        val itemTitle = binding.titleEditText.text.toString().trim()
        if(itemTitle.isEmpty()) {
            binding.titleEditText.error = "* Required field"
            return
        }
        binding.titleEditText.error = null

        val itemDescription = binding.descriptionEditText.text.toString().trim()
        val itemPriority = when(binding.radioGroup.checkedRadioButtonId) {
            R.id.radioButtonLow -> 1
            R.id.radioButtonMedium -> 2
            R.id.radioButtonHigh -> 3
            else -> 0
        }

        val itemEntity = ItemEntity(
            id = UUID.randomUUID().toString(),
            title = itemTitle,
            description = itemDescription,
            priority = itemPriority,
            createdAt = System.currentTimeMillis(),
            categoryId = "" // todo update later when we have categories in the app
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
}