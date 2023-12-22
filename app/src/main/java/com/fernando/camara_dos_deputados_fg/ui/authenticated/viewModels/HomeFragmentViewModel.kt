package com.fernando.camara_dos_deputados_fg.ui.authenticated.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fernando.camara_dos_deputados_fg.api.CamaraDosDeputadosAPI
import com.fernando.camara_dos_deputados_fg.dtos.PartidoList
import com.fernando.camara_dos_deputados_fg.models.Partido
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentViewModel : ViewModel() {
    private val _partidos = MutableLiveData<List<Partido>>()
    private val _errorMessage = MutableLiveData<String>()

    val partidos : LiveData<List<Partido>> = _partidos
    val errorMessage : LiveData<String> = _errorMessage


    fun requestPartidos() {
        val partidoService = CamaraDosDeputadosAPI.partidoService

        partidoService.findAllPartidos().enqueue(object: Callback<PartidoList>{
            override fun onResponse(call: Call<PartidoList>, response: Response<PartidoList>) {
                if (response.isSuccessful) {
                    val partidos = response.body()
                    partidos?.let { _partidos.value = it.partidos }
                }
            }
            override fun onFailure(call: Call<PartidoList>, t: Throwable) {
                _errorMessage.value = t.message
            }

        })
    }

}