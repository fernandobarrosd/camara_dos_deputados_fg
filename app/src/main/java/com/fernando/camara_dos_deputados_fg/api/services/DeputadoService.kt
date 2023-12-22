package com.fernando.camara_dos_deputados_fg.api.services

import com.fernando.camara_dos_deputados_fg.dtos.DeputadoList
import com.fernando.camara_dos_deputados_fg.dtos.DeputadoResponse
import com.fernando.camara_dos_deputados_fg.models.Deputado
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeputadoService {
    @GET("deputados")
    fun findAllDeputados(@Query("ordem") order: String,
                         @Query("ordenarPor") orderBy:
                         String, @Query("itens") itens: Int) : Call<DeputadoList>

    @GET("deputados/{id}")
    fun findDeputadoByID(@Path("id") id : Long) : Call<DeputadoResponse>
}