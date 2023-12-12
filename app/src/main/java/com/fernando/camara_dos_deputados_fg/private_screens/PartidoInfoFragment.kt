package com.fernando.camara_dos_deputados_fg.private_screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.fernando.camara_dos_deputados_fg.api.CamaraDosDeputadosAPI
import com.fernando.camara_dos_deputados_fg.databinding.FragmentPartidoInfoBinding
import com.fernando.camara_dos_deputados_fg.dtos.PartidoResponse
import com.fernando.camara_dos_deputados_fg.models.Partido
import com.fernando.camara_dos_deputados_fg.services.PartidoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PartidoInfoFragment : Fragment() {
    private lateinit var binding: FragmentPartidoInfoBinding
    private val args : PartidoInfoFragmentArgs by navArgs()
    private lateinit var partidoService: PartidoService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentPartidoInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initService()
        requestPartido()
    }

    private fun requestPartido() {
        val partidoID = args.id

        partidoService.findPartidoByID(partidoID).enqueue(object: Callback<PartidoResponse>{
            override fun onResponse(call: Call<PartidoResponse>, response: Response<PartidoResponse>) {
                if (response.isSuccessful) {
                    val partidoResponse = response.body()
                    val partido = partidoResponse?.partido

                    binding.apply {
                        partidoNomeTextView.text = partido?.nome
                        partidoSiglaTextView.text = partido?.sigla
                    }
                }
            }

            override fun onFailure(call: Call<PartidoResponse>, t: Throwable) {
                println(t.message)
            }

        })
    }

    private fun initService() {
        partidoService = CamaraDosDeputadosAPI.partidoService
    }
}