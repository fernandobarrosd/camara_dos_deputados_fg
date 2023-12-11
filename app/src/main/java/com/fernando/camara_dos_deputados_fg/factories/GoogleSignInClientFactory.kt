package com.fernando.camara_dos_deputados_fg.factories

import android.content.Context
import com.fernando.camara_dos_deputados_fg.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

interface GoogleSignInClientFactory {
    companion object {
        fun createClient(context: Context): GoogleSignInClient {
            val options = GoogleSignInOptions.Builder()
                .requestIdToken(context.getString(R.string.client_id))
                .requestEmail()
                .build()
            return GoogleSignIn.getClient(context, options)
        }
    }
}