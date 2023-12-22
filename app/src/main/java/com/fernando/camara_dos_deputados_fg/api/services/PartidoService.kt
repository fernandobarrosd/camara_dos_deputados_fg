package com.fernando.camara_dos_deputados_fg.api.services

import com.fernando.camara_dos_deputados_fg.dtos.PartidoList
import com.fernando.camara_dos_deputados_fg.dtos.PartidoResponse
import com.fernando.camara_dos_deputados_fg.models.Partido
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PartidoService {
    @GET("partidos")
    fun findAllPartidos() : Call<PartidoList>

    @GET("partidos/{id}")
    fun findPartidoByID(@Path("id") id: Long) : Call<PartidoResponse>
}