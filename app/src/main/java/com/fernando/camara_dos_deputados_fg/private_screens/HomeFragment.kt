package com.fernando.camara_dos_deputados_fg.private_screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fernando.camara_dos_deputados_fg.adapters.PartidoAdapter
import com.fernando.camara_dos_deputados_fg.api.CamaraDosDeputadosAPI
import com.fernando.camara_dos_deputados_fg.databinding.FragmentHomeBinding
import com.fernando.camara_dos_deputados_fg.dtos.PartidoList
import com.fernando.camara_dos_deputados_fg.interfaces.OnClickAdapterItemListener
import com.fernando.camara_dos_deputados_fg.models.Partido
import com.fernando.camara_dos_deputados_fg.services.PartidoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var partidoService: PartidoService
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initService()
        requestPartidos()
        initListeners()

    }

    private fun initListeners() {
        binding.root.setOnRefreshListener {
            requestPartidos()
        }
    }

    private fun initService() {
        partidoService = CamaraDosDeputadosAPI.partidoService
    }

    private fun requestPartidos() {
        partidoService.findAllPartidos().enqueue(object: Callback<PartidoList> {
            override fun onResponse(call: Call<PartidoList>, response: Response<PartidoList>) {
                if (binding.root.isRefreshing) {
                    binding.root.isRefreshing = false
                }
                if (response.isSuccessful) {
                    val partidos = response.body()

                    partidos?.let {
                        requireActivity().runOnUiThread {
                            val partidoAdapter = PartidoAdapter(it.partidos)

                            partidoAdapter.setOnClickAdapterItemListener(object: OnClickAdapterItemListener<Partido>{
                                override fun onClickAdapterItem(item: Partido) {
                                    val action = HomeFragmentDirections.actionHomeFragmentToPartidoInfoFragment(item.id)
                                    findNavController().navigate(action)
                                }
                            })

                            binding.recyclerView.adapter = partidoAdapter
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PartidoList>, t: Throwable) {

            }

        })
    }
}