package com.fernando.camara_dos_deputados_fg.dtos

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class DeputadoResponse(
    @JsonProperty("dados")
    val dados: Dados
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Dados(
    @JsonProperty("id")
    val id: Long,

    @JsonProperty("ultimoStatus")
    val status: Status
)


@JsonIgnoreProperties(ignoreUnknown = true)
data class Status(
    @JsonProperty("nome")
    val nome: String,

    @JsonProperty("siglaPartido")
    val siglaPartido: String,

    @JsonProperty("siglaUf")
    val siglaUF: String,

    @JsonProperty("urlFoto")
    val urlFoto: String
)
