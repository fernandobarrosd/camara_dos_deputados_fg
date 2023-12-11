package com.fernando.camara_dos_deputados_fg.factories

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

interface AlertDialogFactory {
    companion object {
        @JvmStatic
        fun createEmailAndPasswordErrorDialog(context: Context?): AlertDialog? {
            val alertDialogBuilder = AlertDialog.Builder(
                context!!
            )
            alertDialogBuilder.setTitle("Erro no login")
            alertDialogBuilder.setMessage("E-mail ou senha inválidos")
            alertDialogBuilder.setPositiveButton("Ok") { dialog: DialogInterface, item: Int -> dialog.dismiss() }
            return alertDialogBuilder.create()
        }
    }
}