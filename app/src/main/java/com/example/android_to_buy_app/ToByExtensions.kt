package com.example.android_to_buy_app

import com.airbnb.epoxy.EpoxyController
import com.example.android_to_buy_app.ui.epoxy.models.HeaderEpoxyModel

fun EpoxyController.addHeaderModel(headerText: String) {
    HeaderEpoxyModel(headerText).id(headerText).addTo(this)
}