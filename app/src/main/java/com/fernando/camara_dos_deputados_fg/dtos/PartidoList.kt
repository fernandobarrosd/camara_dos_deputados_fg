package com.fernando.camara_dos_deputados_fg.dtos

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fernando.camara_dos_deputados_fg.models.Partido

@JsonIgnoreProperties("links")
data class PartidoList(
    @JsonProperty("dados")
    val partidos : List<Partido>
)
