package com.x5bart.translatenordic.ui.dialog

import com.x5bart.translatenordic.model.Favorite

interface OnFavPressed {
    fun onPressed(favorite: Favorite)

    fun onLongPressed(favorite: Favorite)
}