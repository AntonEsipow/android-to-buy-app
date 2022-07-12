package com.example.android_to_buy_app.ui.epoxy.models

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.dmp.tobuy.ui.epoxy.ViewBindingKotlinModel
import com.example.android_to_buy_app.R
import com.example.android_to_buy_app.databinding.ModelHeaderItemBinding

data class HeaderEpoxyModel(
    val headerText: String,
): ViewBindingKotlinModel<ModelHeaderItemBinding>(R.layout.model_header_item) {

    override fun ModelHeaderItemBinding.bind() {
        textView.text = headerText
    }

    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }
}