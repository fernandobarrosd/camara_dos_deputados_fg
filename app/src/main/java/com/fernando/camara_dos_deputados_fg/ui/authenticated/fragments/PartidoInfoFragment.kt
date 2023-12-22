package com.fernando.camara_dos_deputados_fg.ui.authenticated.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fernando.camara_dos_deputados_fg.databinding.FragmentPartidoInfoBinding
import com.fernando.camara_dos_deputados_fg.ui.authenticated.viewModels.PartidoInfoFragmentViewModel
import com.fernando.camara_dos_deputados_fg.ui.utils.ViewUtils

class PartidoInfoFragment : Fragment() {
    private lateinit var binding: FragmentPartidoInfoBinding
    private val partidoInfoFragmentVewModel by viewModels<PartidoInfoFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentPartidoInfoBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservables()
        setRequestStartLoading()
        requestPartido()
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

        partidoInfoFragmentVewModel.requestPartido(partidoID)
    }
}