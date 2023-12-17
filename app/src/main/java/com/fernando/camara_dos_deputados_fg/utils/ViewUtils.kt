package com.fernando.camara_dos_deputados_fg.utils

import android.view.View

class ViewUtils {
    companion object {
        fun setVisibility(view: View, isVisible: Boolean) {
            view.visibility = if (isVisible) View.VISIBLE else View.GONE
        }
    }
}