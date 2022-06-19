package com.example.android_to_buy_app.ui

import androidx.fragment.app.Fragment
import com.example.android_to_buy_app.database.AppDatabase
import java.lang.Appendable

abstract class BaseFragment: Fragment() {

    protected val mainActivity: MainActivity
        get() = activity as MainActivity

    protected val appDataBase: AppDatabase
        get() = AppDatabase.getDatabase(requireActivity())
}