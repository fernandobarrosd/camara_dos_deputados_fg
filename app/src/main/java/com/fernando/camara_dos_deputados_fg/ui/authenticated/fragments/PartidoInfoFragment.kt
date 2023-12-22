package com.fernando.camara_dos_deputados_fg.ui.authenticated.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.fernando.camara_dos_deputados_fg.R
import com.fernando.camara_dos_deputados_fg.ui.utils.FragmentViewBinding
import com.fernando.camara_dos_deputados_fg.databinding.FragmentPartidoInfoBinding
import com.fernando.camara_dos_deputados_fg.ui.authenticated.viewModels.PartidoInfoFragmentViewModel
import com.fernando.camara_dos_deputados_fg.ui.utils.ViewUtils

class PartidoInfoFragment : FragmentViewBinding<FragmentPartidoInfoBinding>() {
    private val partidoInfoFragmentVewModel by viewModels<PartidoInfoFragmentViewModel>()
    override fun inflate(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        atachToParent: Boolean?): FragmentPartidoInfoBinding {
        return FragmentPartidoInfoBinding.inflate(layoutInflater, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOnBackHandler()
        initObservables()
        setRequestStartLoading()
        requestPartido()
    }

    private fun initOnBackHandler() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.supportFragmentManager?.commit {
                replace(R.id.fragmentContainer, HomeFragment())
            }
        }
    }

    private fun initObservables() {
        partidoInfoFragmentVewModel.partido.observe(requireActivity()) {
            setRequestSuccessLoading()

            binding.apply {
                nomeCardText.setText(it.nome)
                siglaCardText.setText(it.sigla)
            }
        }

        partidoInfoFragmentVewModel.errorMessage.observe(requireActivity()) {
            setRequestErrorLoading()
        }
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

        partidoInfoFragmentVewModel.requestPartidoByID(partidoID)
    }
}