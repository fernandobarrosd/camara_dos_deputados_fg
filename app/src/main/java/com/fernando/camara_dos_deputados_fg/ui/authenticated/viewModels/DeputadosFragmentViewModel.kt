package com.fernando.camara_dos_deputados_fg.ui.authenticated.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fernando.camara_dos_deputados_fg.api.CamaraDosDeputadosAPI
import com.fernando.camara_dos_deputados_fg.dtos.DeputadoList
import com.fernando.camara_dos_deputados_fg.models.Deputado
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeputadosFragmentViewModel : ViewModel() {
    private val _deputados = MutableLiveData<List<Deputado>>()
    private val _errorMessage = MutableLiveData<String>()

    val deputados : LiveData<List<Deputado>> = _deputados
    val errorMessage : LiveData<String> = _errorMessage

    fun requestDeputados() {
        val deputadoService = CamaraDosDeputadosAPI.deputadoService

        deputadoService.findAllDeputados("ASC", "nome", 500)
            .enqueue(object: Callback<DeputadoList>{
                override fun onResponse(
                    call: Call<DeputadoList>,
                    response: Response<DeputadoList>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val deputados = it.deputados
                            _deputados.value = deputados
                        }
                    }
                }

                override fun onFailure(call: Call<DeputadoList>, t: Throwable) {
                    _errorMessage.value = t.message
                }

            })
    }
}