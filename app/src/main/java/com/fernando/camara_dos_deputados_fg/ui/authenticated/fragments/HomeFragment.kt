package com.fernando.camara_dos_deputados_fg.ui.authenticated.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
<<<<<<< HEAD:app/src/main/java/com/fernando/camara_dos_deputados_fg/ui/authenticated/fragments/HomeFragment.kt
import androidx.fragment.app.viewModels
=======
import com.fernando.camara_dos_deputados_fg.FragmentViewBinding
>>>>>>> 8b1e9744cb8dd365c6074ea715055e9b21296dd6:app/src/main/java/com/fernando/camara_dos_deputados_fg/private_screens/HomeFragment.kt
import com.fernando.camara_dos_deputados_fg.R
import com.fernando.camara_dos_deputados_fg.ui.adapters.CardSkeletonAdapter
import com.fernando.camara_dos_deputados_fg.ui.adapters.PartidoAdapter
import com.fernando.camara_dos_deputados_fg.databinding.FragmentHomeBinding
import com.fernando.camara_dos_deputados_fg.interfaces.OnClickAdapterItemListener
import com.fernando.camara_dos_deputados_fg.models.Partido
import com.fernando.camara_dos_deputados_fg.ui.authenticated.viewModels.HomeFragmentViewModel

<<<<<<< HEAD:app/src/main/java/com/fernando/camara_dos_deputados_fg/ui/authenticated/fragments/HomeFragment.kt
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeFragmentViewModel by viewModels<HomeFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
=======
class HomeFragment : FragmentViewBinding<FragmentHomeBinding>() {
    private lateinit var partidoService: PartidoService
    override fun inflate(
        layoutInflater: LayoutInflater,
>>>>>>> 8b1e9744cb8dd365c6074ea715055e9b21296dd6:app/src/main/java/com/fernando/camara_dos_deputados_fg/private_screens/HomeFragment.kt
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