package com.fernando.camara_dos_deputados_fg.private_screens

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.fernando.camara_dos_deputados_fg.FragmentViewBinding
import com.fernando.camara_dos_deputados_fg.R
import com.fernando.camara_dos_deputados_fg.api.CamaraDosDeputadosAPI
import com.fernando.camara_dos_deputados_fg.databinding.FragmentDeputadoInfoBinding
import com.fernando.camara_dos_deputados_fg.dtos.DeputadoResponse
import com.fernando.camara_dos_deputados_fg.models.Deputado
import com.fernando.camara_dos_deputados_fg.services.DeputadoService
import com.fernando.camara_dos_deputados_fg.utils.ViewUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DeputadoInfoFragment : FragmentViewBinding<FragmentDeputadoInfoBinding>() {
    private lateinit var deputadoService: DeputadoService

    override fun inflate(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        atachToParent: Boolean?): FragmentDeputadoInfoBinding {
        return FragmentDeputadoInfoBinding.inflate(layoutInflater, container, false)
    }

    private fun setRequestSuccessLoading() {
        ViewUtils.setVisibility(binding.deputadoInfoView, true)
        ViewUtils.setVisibility(binding.loading, false)
    }

    private fun setRequestErrorLoading() {
        ViewUtils.setVisibility(binding.deputadoInfoView, false)
        ViewUtils.setVisibility(binding.loading, false)
    }

    private fun setRequestStartLoading() {
        ViewUtils.setVisibility(binding.deputadoInfoView, false)
        ViewUtils.setVisibility(binding.loading, true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initService()
        setRequestStartLoading()
        requestPartidos()
    }

    private fun initService() {
        deputadoService = CamaraDosDeputadosAPI.deputadoService
    }

    private fun requestPartidos() {
        val deputadoID = requireArguments().getLong("id")
        deputadoService.findDeputadoByID(deputadoID).enqueue(object: Callback<DeputadoResponse>{
            override fun onResponse(call: Call<DeputadoResponse>, response: Response<DeputadoResponse>) {
                if (response.isSuccessful) {
                    setRequestSuccessLoading()
                    val deputado = response.body()
                    setDeputadoProfile(deputado)
                }
            }

            override fun onFailure(call: Call<DeputadoResponse>, t: Throwable) {
                setRequestErrorLoading()
            }

        })
    }

    private fun setDeputadoImage(urlFoto: String) {
        Glide.with(this)
            .load(urlFoto)
            .into(binding.imageView)
    }

    private fun setDeputadoProfile(deputado: DeputadoResponse?) {
        binding.apply {
            deputado?.dados?.status?.let {
                println(it.urlFoto)
                nomeCardText.setText(it.nome)
                siglaPartidoCardText.setText(it.siglaPartido)
                setDeputadoImage(it.urlFoto)
            }
        }
    }


}