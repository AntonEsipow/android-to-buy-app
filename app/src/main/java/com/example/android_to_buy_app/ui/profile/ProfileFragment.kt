package com.example.android_to_buy_app.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_to_buy_app.R
import com.example.android_to_buy_app.databinding.FragmentProfileBinding
import com.example.android_to_buy_app.ui.BaseFragment

class ProfileFragment: BaseFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileEpoxyController = ProfileEpoxyController(
        onCategoryEmptyStateClicked = ::onCategoryEmptyStateClicked
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideKeyboard()
        binding.epoxyRecyclerView.setController(profileEpoxyController)

        sharedViewModel.categoryEntitiesLiveData.observe(viewLifecycleOwner) { categoryEntityList ->
            profileEpoxyController.categories = categoryEntityList
        }
    }

    private fun onCategoryEmptyStateClicked() {
        navigateViaNavGraph(R.id.action_profileFragment_to_addCategoryFragment)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}