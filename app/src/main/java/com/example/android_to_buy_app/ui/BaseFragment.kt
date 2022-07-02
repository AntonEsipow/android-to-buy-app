package com.example.android_to_buy_app.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.android_to_buy_app.arch.ToBuyViewModel
import com.example.android_to_buy_app.database.AppDatabase
import java.lang.Appendable

abstract class BaseFragment: Fragment() {

    protected val mainActivity: MainActivity
        get() = activity as MainActivity

    protected val appDataBase: AppDatabase
        get() = AppDatabase.getDatabase(requireActivity())

    protected val sharedViewModel: ToBuyViewModel by activityViewModels()

    // region Navigation helper methods
    protected fun navigateUp() {
        mainActivity.navController.navigateUp()
    }

    protected fun navigateViaNavGraph(actionId: Int) {
        mainActivity.navController.navigate(actionId)
    }
    // endregion Navigation helper methods
}