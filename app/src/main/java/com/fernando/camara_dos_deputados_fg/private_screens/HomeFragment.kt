package com.fernando.camara_dos_deputados_fg.private_screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import com.fernando.camara_dos_deputados_fg.FragmentViewBinding
import com.fernando.camara_dos_deputados_fg.R
import com.fernando.camara_dos_deputados_fg.adapters.CardSkeletonAdapter
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

class HomeFragment : FragmentViewBinding<FragmentHomeBinding>() {
    private lateinit var partidoService: PartidoService
    override fun inflate(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        atachToParent: Boolean?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initService()
        setRecylerViewWithCardSkeletonAdapter()
        requestPartidos()
        initListeners()

    }

    private fun initListeners() {
        binding.root.setOnRefreshListener {
            setRecylerViewWithCardSkeletonAdapter()
            requestPartidos()
        }
    }

    private fun initService() {
        partidoService = CamaraDosDeputadosAPI.partidoService
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
                            setRecylerViewWithPartidoAdapter(it.partidos)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PartidoList>, t: Throwable) {
                binding.recyclerView.adapter = null
            }

        })
    }
}