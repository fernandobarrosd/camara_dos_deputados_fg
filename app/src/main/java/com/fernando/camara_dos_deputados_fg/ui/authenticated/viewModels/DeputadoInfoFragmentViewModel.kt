package com.fernando.camara_dos_deputados_fg.ui.authenticated.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fernando.camara_dos_deputados_fg.api.CamaraDosDeputadosAPI
import com.fernando.camara_dos_deputados_fg.dtos.DeputadoResponse
import com.fernando.camara_dos_deputados_fg.models.Deputado
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeputadoInfoFragmentViewModel : ViewModel() {
    private val _deputado = MutableLiveData<Deputado>()
    private val _errorMessage = MutableLiveData<String>()

    val deputado : LiveData<Deputado> = _deputado
    val errorMessage : LiveData<String> = _errorMessage


    fun requestDeputadoByID(deputadoID: Long) {
        val deputadoService = CamaraDosDeputadosAPI.deputadoService

        deputadoService.findDeputadoByID(deputadoID).enqueue(object: Callback<DeputadoResponse> {
            override fun onResponse(call: Call<DeputadoResponse>, response: Response<DeputadoResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val dados = it.dados
                        val status = dados.status
                        val deputado = Deputado(
                            deputadoID, status.nome, status.siglaPartido,
                            status.siglaUF, status.urlFoto)

                        _deputado.value = deputado
                    }
                }
            }

            override fun onFailure(call: Call<DeputadoResponse>, t: Throwable) {
                _errorMessage.value = t.message
            }

        })
    }

}