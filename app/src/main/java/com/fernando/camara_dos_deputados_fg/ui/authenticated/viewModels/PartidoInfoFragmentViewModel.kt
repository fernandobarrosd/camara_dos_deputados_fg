package com.fernando.camara_dos_deputados_fg.ui.authenticated.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fernando.camara_dos_deputados_fg.api.CamaraDosDeputadosAPI
import com.fernando.camara_dos_deputados_fg.dtos.PartidoResponse
import com.fernando.camara_dos_deputados_fg.models.Partido
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PartidoInfoFragmentViewModel : ViewModel() {
    private val _partido = MutableLiveData<Partido>()
    private val _errorMessage = MutableLiveData<String>()

    val partido : LiveData<Partido> = _partido
    val errorMessage : LiveData<String> = _errorMessage

    fun requestPartidoByID(partidoID: Long) {
        val partidoService = CamaraDosDeputadosAPI.partidoService

        partidoService.findPartidoByID(partidoID).enqueue(object: Callback<PartidoResponse>{
            override fun onResponse(
                call: Call<PartidoResponse>,
                response: Response<PartidoResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val partido = it.partido
                        _partido.value = partido
                    }
                }

            }

            override fun onFailure(call: Call<PartidoResponse>, t: Throwable) {
                _errorMessage.value = t.message
            }

        })
    }
}