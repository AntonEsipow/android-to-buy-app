package com.example.android_to_buy_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_to_buy_app.R
import com.example.android_to_buy_app.databinding.FragmentHomeBinding
import com.example.android_to_buy_app.ui.BaseFragment
import java.util.*

class HomeFragment: BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            navigateViaNavGraph(R.id.action_homeFragment_to_addItemEntityFragment)
        }

        sharedViewModel.itemEntitiesLiveData.observe(viewLifecycleOwner) { itemEntityList ->
            // todo
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}