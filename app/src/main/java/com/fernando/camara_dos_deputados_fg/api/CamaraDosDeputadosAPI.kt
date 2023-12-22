package com.fernando.camara_dos_deputados_fg.api

import com.fernando.camara_dos_deputados_fg.api.services.DeputadoService
import com.fernando.camara_dos_deputados_fg.api.services.PartidoService
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

interface CamaraDosDeputadosAPI {
    companion object {
        private const val BASE_URL = "https://dadosabertos.camara.leg.br/api/v2/"
        private val retrofit = Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

        val partidoService = retrofit.create(PartidoService::class.java)
        val deputadoService = retrofit.create(DeputadoService::class.java)

    }
}