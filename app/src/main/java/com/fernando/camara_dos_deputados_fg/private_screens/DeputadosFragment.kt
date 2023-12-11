package com.fernando.camara_dos_deputados_fg.private_screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fernando.camara_dos_deputados_fg.adapters.DeputadoAdapter
import com.fernando.camara_dos_deputados_fg.api.CamaraDosDeputadosAPI
import com.fernando.camara_dos_deputados_fg.databinding.FragmentDeputadosBinding
import com.fernando.camara_dos_deputados_fg.dtos.DeputadoList
import com.fernando.camara_dos_deputados_fg.services.DeputadoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeputadosFragment : Fragment() {
    private lateinit var binding: FragmentDeputadosBinding
    private lateinit var deputadoService: DeputadoService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentDeputadosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initService()
        requestDeputados()
        initListener()
    }

    private fun initService() {
        deputadoService = CamaraDosDeputadosAPI.deputadoService
    }

    private fun initListener() {
        binding.root.setOnRefreshListener {
            requestDeputados()
        }
    }



    private fun requestDeputados() {
        deputadoService.findAllDeputados("ASC", "nome", 40)
            .enqueue(object: Callback<DeputadoList>{
                override fun onResponse(call: Call<DeputadoList>, response: Response<DeputadoList>) {
                    if (binding.root.isRefreshing) {
                        binding.root.isRefreshing = false
                    }
                    if (response.isSuccessful) {
                        response.body()?.let {deputadoList ->
                            val deputados = deputadoList.deputados
                            val deputadoAdapter = DeputadoAdapter(deputados)

                            binding.recyclerView.adapter = deputadoAdapter
                        }
                    }
                }

                override fun onFailure(call: Call<DeputadoList>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }
}