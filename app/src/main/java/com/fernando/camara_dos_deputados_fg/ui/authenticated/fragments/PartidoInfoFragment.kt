package com.fernando.camara_dos_deputados_fg.ui.authenticated.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
<<<<<<< HEAD:app/src/main/java/com/fernando/camara_dos_deputados_fg/ui/authenticated/fragments/PartidoInfoFragment.kt
import androidx.fragment.app.viewModels
=======
import androidx.navigation.fragment.navArgs
import com.fernando.camara_dos_deputados_fg.FragmentViewBinding
import com.fernando.camara_dos_deputados_fg.api.CamaraDosDeputadosAPI
import com.fernando.camara_dos_deputados_fg.databinding.FragmentDeputadosBinding
>>>>>>> 8b1e9744cb8dd365c6074ea715055e9b21296dd6:app/src/main/java/com/fernando/camara_dos_deputados_fg/private_screens/PartidoInfoFragment.kt
import com.fernando.camara_dos_deputados_fg.databinding.FragmentPartidoInfoBinding
import com.fernando.camara_dos_deputados_fg.ui.authenticated.viewModels.PartidoInfoFragmentViewModel
import com.fernando.camara_dos_deputados_fg.ui.utils.ViewUtils

<<<<<<< HEAD:app/src/main/java/com/fernando/camara_dos_deputados_fg/ui/authenticated/fragments/PartidoInfoFragment.kt
class PartidoInfoFragment : Fragment() {
    private lateinit var binding: FragmentPartidoInfoBinding
    private val partidoInfoFragmentVewModel by viewModels<PartidoInfoFragmentViewModel>()
=======
class PartidoInfoFragment : FragmentViewBinding<FragmentPartidoInfoBinding>() {
    private lateinit var partidoService: PartidoService
>>>>>>> 8b1e9744cb8dd365c6074ea715055e9b21296dd6:app/src/main/java/com/fernando/camara_dos_deputados_fg/private_screens/PartidoInfoFragment.kt

    override fun inflate(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        atachToParent: Boolean?): FragmentPartidoInfoBinding {
        return FragmentPartidoInfoBinding.inflate(layoutInflater, container, false)
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

<<<<<<< HEAD:app/src/main/java/com/fernando/camara_dos_deputados_fg/ui/authenticated/fragments/PartidoInfoFragment.kt
        partidoInfoFragmentVewModel.requestPartido(partidoID)
=======
        partidoService.findPartidoByID(partidoID).enqueue(object: Callback<PartidoResponse>{
            override fun onResponse(call: Call<PartidoResponse>, response: Response<PartidoResponse>) {
                if (response.isSuccessful) {
                    setRequestSuccessLoading()
                    val partido = response.body()
                    setPartidoInfo(partido)
                }
            }

            override fun onFailure(call: Call<PartidoResponse>, t: Throwable) {
                setRequestErrorLoading()
            }

        })
    }

    private fun setPartidoInfo(partido: PartidoResponse?) {
        binding.apply {
            partido?.partido?.let {
                nomeCardText.setText(it.nome)
                siglaCardText.setText(it.sigla)
            }
        }
    }

    private fun initService() {
        partidoService = CamaraDosDeputadosAPI.partidoService
>>>>>>> 8b1e9744cb8dd365c6074ea715055e9b21296dd6:app/src/main/java/com/fernando/camara_dos_deputados_fg/private_screens/PartidoInfoFragment.kt
    }
}