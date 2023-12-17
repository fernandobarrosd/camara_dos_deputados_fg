package com.fernando.camara_dos_deputados_fg

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class ActivityViewBinding<T : ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: T

    abstract fun inflate(layoutInflater: LayoutInflater) : T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
    }
}