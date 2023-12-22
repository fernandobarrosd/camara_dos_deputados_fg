package com.fernando.camara_dos_deputados_fg.ui.authenticated.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.fernando.camara_dos_deputados_fg.R
import com.fernando.camara_dos_deputados_fg.ui.utils.FragmentViewBinding
import com.fernando.camara_dos_deputados_fg.api.CamaraDosDeputadosAPI
import com.fernando.camara_dos_deputados_fg.databinding.FragmentDeputadoInfoBinding
import com.fernando.camara_dos_deputados_fg.dtos.DeputadoResponse
import com.fernando.camara_dos_deputados_fg.api.services.DeputadoService
import com.fernando.camara_dos_deputados_fg.ui.authenticated.viewModels.DeputadoInfoFragmentViewModel
import com.fernando.camara_dos_deputados_fg.ui.utils.ViewUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DeputadoInfoFragment : FragmentViewBinding<FragmentDeputadoInfoBinding>() {
    private val deputadoInfoFragmentViewModel by viewModels<DeputadoInfoFragmentViewModel>()

    override fun inflate(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        atachToParent: Boolean?): FragmentDeputadoInfoBinding {
        return FragmentDeputadoInfoBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservables()
        initOnBackHandler()
        setRequestStartLoading()
        requestDeputado()
    }

    private fun requestDeputado() {
        val deputadoID = requireArguments().getLong("id")
        deputadoInfoFragmentViewModel.requestDeputadoByID(deputadoID)
    }

    private fun initOnBackHandler() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.supportFragmentManager?.commit {
                replace(R.id.fragmentContainer, DeputadosFragment())
            }
        }
    }

    private fun initObservables() {
        deputadoInfoFragmentViewModel.deputado.observe(requireActivity()) {
            setRequestSuccessLoading()
            binding.apply {
                nomeCardText.setText(it.nome)
                siglaPartidoCardText.setText(it.siglaPartido)
                setDeputadoImage(it.urlFoto)
            }
        }

        deputadoInfoFragmentViewModel.errorMessage.observe(requireActivity()) {
            setRequestErrorLoading()
        }
    }

    private fun setDeputadoImage(urlFoto: String) {
        Glide.with(this)
            .load(urlFoto)
            .into(binding.imageView)
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

}