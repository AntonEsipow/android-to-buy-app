package com.example.android_to_buy_app.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.android_to_buy_app.R
import com.example.android_to_buy_app.arch.ToBuyViewModel
import com.example.android_to_buy_app.database.AppDatabase

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: ToBuyViewModel by viewModels()
        viewModel.init(AppDatabase.getDatabase(this))
    }
}