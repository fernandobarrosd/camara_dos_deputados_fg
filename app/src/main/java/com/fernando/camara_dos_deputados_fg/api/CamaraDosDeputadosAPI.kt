package com.fernando.camara_dos_deputados_fg.api

import com.fernando.camara_dos_deputados_fg.services.DeputadoService
import com.fernando.camara_dos_deputados_fg.services.PartidoService
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class CamaraDosDeputadosAPI {
    companion object {
        private val apiURL = "https://dadosabertos.camara.leg.br/api/v2/"
        private val retrofit = Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create())
            .baseUrl(apiURL)
            .build()

        val partidoService = retrofit.create(PartidoService::class.java)
        val deputadoService = retrofit.create(DeputadoService::class.java)

    }
}