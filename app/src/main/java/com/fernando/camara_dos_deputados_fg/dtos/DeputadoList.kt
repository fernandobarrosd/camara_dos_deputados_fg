package com.fernando.camara_dos_deputados_fg.dtos

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fernando.camara_dos_deputados_fg.models.Deputado

@JsonIgnoreProperties(ignoreUnknown = true)
data class DeputadoList(
    @JsonProperty("dados")
    val deputados: List<Deputado>
)