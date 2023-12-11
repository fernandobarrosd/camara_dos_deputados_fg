package com.fernando.camara_dos_deputados_fg.private_screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fernando.camara_dos_deputados_fg.databinding.FragmentUpdateEmailBinding

class UpdateEmailFragment : Fragment() {
    private lateinit var binding: FragmentUpdateEmailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentUpdateEmailBinding.inflate(inflater, container, false)
        return binding.root
    }


}