package com.fernando.camara_dos_deputados_fg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class FragmentViewBinding<T : ViewBinding> : Fragment() {
    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = inflate(inflater, container, false)
        return binding.root
    }

    abstract fun inflate(layoutInflater: LayoutInflater, container: ViewGroup?, atachToParent: Boolean?) : T
}