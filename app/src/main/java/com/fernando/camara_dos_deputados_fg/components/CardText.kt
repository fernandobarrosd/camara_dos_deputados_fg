package com.fernando.camara_dos_deputados_fg.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.fernando.camara_dos_deputados_fg.R
import com.fernando.camara_dos_deputados_fg.databinding.LayoutCardTextBinding

class CardText(context: Context, attrs: AttributeSet?) : CardView(context, attrs) {
    private var typedArray: TypedArray
    private val binding : LayoutCardTextBinding = LayoutCardTextBinding.inflate(
        LayoutInflater.from(context), this, true)

    init {
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.CardText)
        binding.textView.text = typedArray.getString(R.styleable.CardText_text)
    }

    fun setText(text: String) {
        binding.textView.text = text
    }


}