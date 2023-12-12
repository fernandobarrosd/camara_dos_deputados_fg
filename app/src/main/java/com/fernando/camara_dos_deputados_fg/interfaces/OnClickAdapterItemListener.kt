package com.fernando.camara_dos_deputados_fg.interfaces

@FunctionalInterface
interface OnClickAdapterItemListener<T> {
    fun onClickAdapterItem(item: T)
}