package com.fernando.camara_dos_deputados_fg.private_screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fernando.camara_dos_deputados_fg.FragmentViewBinding
import com.fernando.camara_dos_deputados_fg.adapters.CardSkeletonAdapter
import com.fernando.camara_dos_deputados_fg.adapters.DeputadoAdapter
import com.fernando.camara_dos_deputados_fg.api.CamaraDosDeputadosAPI
import com.fernando.camara_dos_deputados_fg.databinding.FragmentDeputadosBinding
import com.fernando.camara_dos_deputados_fg.dtos.DeputadoList
import com.fernando.camara_dos_deputados_fg.models.Deputado
import com.fernando.camara_dos_deputados_fg.services.DeputadoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeputadosFragment : FragmentViewBinding<FragmentDeputadosBinding>() {
    private lateinit var deputadoService: DeputadoService
    override fun inflate(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        atachToParent: Boolean?): FragmentDeputadosBinding {
        return FragmentDeputadosBinding.inflate(layoutInflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initService()
        setRecylerViewWithCardSkeletonAdapter()
        requestDeputados()
        initListener()
    }

    private fun initService() {
        deputadoService = CamaraDosDeputadosAPI.deputadoService
    }

    private fun initListener() {
        binding.root.setOnRefreshListener {
            setRecylerViewWithCardSkeletonAdapter()
            requestDeputados()
        }
    }

    private fun setRecylerViewWithCardSkeletonAdapter() {
        binding.recyclerView.adapter = CardSkeletonAdapter()
    }

    private fun setRecylerViewWithDeputadoAdapter(deputados: List<Deputado>) {
        val deputadoAdapter = DeputadoAdapter(deputados)
        binding.recyclerView.adapter = deputadoAdapter
    }


    private fun requestDeputados() {
        deputadoService.findAllDeputados("ASC", "nome", 500)
            .enqueue(object: Callback<DeputadoList>{
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