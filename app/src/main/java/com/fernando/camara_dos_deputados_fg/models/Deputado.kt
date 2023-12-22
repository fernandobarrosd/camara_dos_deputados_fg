package com.fernando.camara_dos_deputados_fg.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Deputado(
    @JsonProperty("id") val id: Long,
    @JsonProperty("nome") val nome: String,
    @JsonProperty("siglaPartido") val siglaPartido: String,
    @JsonProperty("siglaUf") val siglaUf: String,
    @JsonProperty("urlFoto") val urlFoto: String
)