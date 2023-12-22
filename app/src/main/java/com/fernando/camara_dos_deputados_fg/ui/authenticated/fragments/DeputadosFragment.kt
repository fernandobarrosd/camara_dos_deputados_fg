package com.fernando.camara_dos_deputados_fg.ui.authenticated.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fernando.camara_dos_deputados_fg.ui.adapters.CardSkeletonAdapter
import com.fernando.camara_dos_deputados_fg.ui.adapters.DeputadoAdapter
import com.fernando.camara_dos_deputados_fg.api.CamaraDosDeputadosAPI
import com.fernando.camara_dos_deputados_fg.databinding.FragmentDeputadosBinding
import com.fernando.camara_dos_deputados_fg.dtos.DeputadoList
import com.fernando.camara_dos_deputados_fg.models.Deputado
import com.fernando.camara_dos_deputados_fg.api.services.DeputadoService
import com.fernando.camara_dos_deputados_fg.ui.authenticated.viewModels.DeputadosFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeputadosFragment : Fragment() {
    private lateinit var binding: FragmentDeputadosBinding
    private lateinit var deputadoService: DeputadoService
    private val deputadosFragmentViewModel by viewModels<DeputadosFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentDeputadosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecylerViewWithCardSkeletonAdapter()
        initObservables()
        deputadosFragmentViewModel.requestDeputados()
        initListener()
    }

    private fun initObservables() {
        deputadosFragmentViewModel.deputados.observe(requireActivity()) {
            if (binding.root.isRefreshing) {
                binding.root.isRefreshing = false
            }
            setRecylerViewWithDeputadoAdapter(it)
        }

        deputadosFragmentViewModel.errorMessage.observe(requireActivity()) {
            binding.recyclerView.adapter = null
        }
    }



    private fun initListener() {
        binding.root.setOnRefreshListener {
            setRecylerViewWithCardSkeletonAdapter()
            deputadosFragmentViewModel.requestDeputados()
        }
    }

    private fun setRecylerViewWithCardSkeletonAdapter() {
        binding.recyclerView.adapter = CardSkeletonAdapter()
    }

    private fun setRecylerViewWithDeputadoAdapter(deputados: List<Deputado>) {
        val deputadoAdapter = DeputadoAdapter(deputados)
        binding.recyclerView.adapter = deputadoAdapter
    }
}