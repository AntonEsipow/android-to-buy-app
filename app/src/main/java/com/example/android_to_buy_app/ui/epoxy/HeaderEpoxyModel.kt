package com.example.android_to_buy_app.ui.epoxy

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.dmp.tobuy.ui.epoxy.ViewBindingKotlinModel
import com.example.android_to_buy_app.R
import com.example.android_to_buy_app.databinding.ModelHeaderItemBinding

data class HeaderEpoxyModel(
    val headerText: String,
    val headerColorRes: Int
): ViewBindingKotlinModel<ModelHeaderItemBinding>(R.layout.model_header_item) {

    override fun ModelHeaderItemBinding.bind() {
        textView.text = headerText
        val color = ContextCompat.getColor(root.context, headerColorRes)
        textView.setBackgroundColor(color)
    }
}