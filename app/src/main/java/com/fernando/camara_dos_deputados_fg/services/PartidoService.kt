package com.fernando.camara_dos_deputados_fg.services

import com.fernando.camara_dos_deputados_fg.dtos.PartidoList
import retrofit2.Call
import retrofit2.http.GET

interface PartidoService {
    @GET("partidos")
    fun findAllPartidos() : Call<PartidoList>
}