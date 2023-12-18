package com.fernando.camara_dos_deputados_fg.private_screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.fernando.camara_dos_deputados_fg.FragmentViewBinding
import com.fernando.camara_dos_deputados_fg.api.CamaraDosDeputadosAPI
import com.fernando.camara_dos_deputados_fg.databinding.FragmentDeputadosBinding
import com.fernando.camara_dos_deputados_fg.databinding.FragmentPartidoInfoBinding
import com.fernando.camara_dos_deputados_fg.dtos.PartidoResponse
import com.fernando.camara_dos_deputados_fg.services.PartidoService
import com.fernando.camara_dos_deputados_fg.utils.ViewUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PartidoInfoFragment : FragmentViewBinding<FragmentPartidoInfoBinding>() {
    private lateinit var partidoService: PartidoService

    override fun inflate(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        atachToParent: Boolean?): FragmentPartidoInfoBinding {
        return FragmentPartidoInfoBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initService()
        requestPartido()
    }

    private fun setRequestSuccessLoading() {
        ViewUtils.setVisibility(binding.partidoInfoView, true)
        ViewUtils.setVisibility(binding.loading, false)
    }

    private fun setRequestErrorLoading() {
        ViewUtils.setVisibility(binding.partidoInfoView, false)
        ViewUtils.setVisibility(binding.loading, false)
    }

    private fun setRequestStartLoading() {
        ViewUtils.setVisibility(binding.partidoInfoView, false)
        ViewUtils.setVisibility(binding.loading, true)
    }



    private fun requestPartido() {
        val partidoID = requireArguments().getLong("id")
        setRequestStartLoading()

        partidoService.findPartidoByID(partidoID).enqueue(object: Callback<PartidoResponse>{
            override fun onResponse(call: Call<PartidoResponse>, response: Response<PartidoResponse>) {
                if (response.isSuccessful) {
                    val partidoResponse = response.body()
                    val partido = partidoResponse?.partido
                    setRequestSuccessLoading()


                    binding.apply {
                        partido?.let {
                            nomeCardText.setText(it.nome)
                            siglaCardText.setText(it.sigla)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PartidoResponse>, t: Throwable) {
                setRequestErrorLoading()
            }

        })
    }

    private fun initService() {
        partidoService = CamaraDosDeputadosAPI.partidoService
    }
}