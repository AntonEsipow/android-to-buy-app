package com.example.android_to_buy_app.ui

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
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

    protected fun navigateViaNavGraph(navDirections: NavDirections) {
        mainActivity.navController.navigate(navDirections)
    }
    // endregion Navigation helper methods

    // region Keyboard methods
    fun Fragment.showKeyboard() {
        view?.let { activity?.showKeyboard(it) }
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.showKeyboard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun Context.hideKeyboard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    // endregion Keyboard methods
}