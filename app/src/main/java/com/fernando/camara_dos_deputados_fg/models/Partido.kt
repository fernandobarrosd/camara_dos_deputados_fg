package com.fernando.camara_dos_deputados_fg.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties("uri")
data class Partido(
    @JsonProperty("id") val id: Long,
    @JsonProperty("sigla") val sigla: String,
    @JsonProperty("nome") val nome: String
)