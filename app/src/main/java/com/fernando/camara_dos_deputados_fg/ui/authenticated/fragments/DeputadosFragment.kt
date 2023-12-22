package com.fernando.camara_dos_deputados_fg.ui.authenticated.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fernando.camara_dos_deputados_fg.ui.adapters.CardSkeletonAdapter
import com.fernando.camara_dos_deputados_fg.ui.adapters.DeputadoAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import com.fernando.camara_dos_deputados_fg.FragmentViewBinding
import com.fernando.camara_dos_deputados_fg.R
import com.fernando.camara_dos_deputados_fg.databinding.FragmentDeputadosBinding
import com.fernando.camara_dos_deputados_fg.dtos.DeputadoList
import com.fernando.camara_dos_deputados_fg.models.Deputado
import com.fernando.camara_dos_deputados_fg.api.services.DeputadoService
import com.fernando.camara_dos_deputados_fg.ui.authenticated.viewModels.DeputadosFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeputadosFragment : FragmentViewBinding<FragmentDeputadosBinding>() {
    private lateinit var deputadoService: DeputadoService

    private val deputadosFragmentViewModel by viewModels<DeputadosFragmentViewModel>()

    override fun inflate(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        atachToParent: Boolean?): FragmentDeputadosBinding {
        return FragmentDeputadosBinding.inflate(layoutInflater, container, false)
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

        deputadoAdapter.setOnClickAdapterItemListener {
            val args = bundleOf("id" to it.id)
            val deputadoInfoFragment = DeputadoInfoFragment()
            deputadoInfoFragment.arguments = args

            activity?.supportFragmentManager?.commit {
                replace(R.id.fragmentContainer, deputadoInfoFragment)
            }
        }

        binding.recyclerView.adapter = deputadoAdapter

    }


    private fun requestDeputados() {
        deputadoService.findAllDeputados("ASC", "nome", 500).enqueue(object: Callback<DeputadoList>{
                override fun onResponse(call: Call<DeputadoList>, response: Response<DeputadoList>) {
                    if (binding.root.isRefreshing) {
                        binding.root.isRefreshing = false
                    }
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val deputados = it.deputados
                            setRecylerViewWithDeputadoAdapter(deputados)
                        }
                    }
                }

                override fun onFailure(call: Call<DeputadoList>, t: Throwable) {
                    binding.recyclerView.adapter = null
                }

            })
    }
}