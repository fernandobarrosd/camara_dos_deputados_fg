package com.fernando.camara_dos_deputados_fg.ui.authenticated.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit

import androidx.fragment.app.viewModels

import com.fernando.camara_dos_deputados_fg.ui.utils.FragmentViewBinding
import com.fernando.camara_dos_deputados_fg.R
import com.fernando.camara_dos_deputados_fg.ui.adapters.CardSkeletonAdapter
import com.fernando.camara_dos_deputados_fg.ui.adapters.PartidoAdapter
import com.fernando.camara_dos_deputados_fg.databinding.FragmentHomeBinding
import com.fernando.camara_dos_deputados_fg.models.Partido
import com.fernando.camara_dos_deputados_fg.ui.authenticated.viewModels.HomeFragmentViewModel

class HomeFragment : FragmentViewBinding<FragmentHomeBinding>() {
    private val homeFragmentViewModel by viewModels<HomeFragmentViewModel>()
    override fun inflate(
        layoutInflater: LayoutInflater,

        container: ViewGroup?,
        atachToParent: Boolean?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecylerViewWithCardSkeletonAdapter()
        initObservables()
        homeFragmentViewModel.requestPartidos()
        initListeners()
    }

    private fun initObservables() {
        homeFragmentViewModel.partidos.observe(requireActivity()) {
            if (binding.root.isRefreshing) {
                binding.root.isRefreshing = false
            }
            setRecylerViewWithPartidoAdapter(it)
        }

        homeFragmentViewModel.errorMessage.observe(requireActivity()) {
            binding.recyclerView.adapter = null
        }
    }

    private fun initListeners() {
        binding.root.setOnRefreshListener {
            setRecylerViewWithCardSkeletonAdapter()
            homeFragmentViewModel.requestPartidos()
        }
    }



    private fun setRecylerViewWithCardSkeletonAdapter() {
        binding.recyclerView.adapter = CardSkeletonAdapter()
    }
    private fun setRecylerViewWithPartidoAdapter(partidos: List<Partido>) {
        val partidoAdapter = PartidoAdapter(partidos)

        partidoAdapter.setOnClickAdapterItemListener {
            val args = bundleOf("id" to it.id)
            val partidoInfoFragment = PartidoInfoFragment()
            partidoInfoFragment.arguments = args

            activity?.supportFragmentManager?.commit {
                replace(R.id.fragmentContainer, partidoInfoFragment)
            }
        }

        binding.recyclerView.adapter = partidoAdapter
    }
}