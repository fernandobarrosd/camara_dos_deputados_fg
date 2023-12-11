package com.fernando.camara_dos_deputados_fg.constants

interface Regex {
    companion object {
        val EMAIL = Regex("[a-z]+[a-zA-Z0-9-_]+@[a-z]+(\\.[a-z]+)+")
    }
}