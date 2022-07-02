package com.example.android_to_buy_app.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_to_buy_app.databinding.FragmentAddItemEntityBinding
import com.example.android_to_buy_app.ui.BaseFragment

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

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}