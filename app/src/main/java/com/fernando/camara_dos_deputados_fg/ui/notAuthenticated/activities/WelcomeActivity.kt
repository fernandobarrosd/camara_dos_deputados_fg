package com.fernando.camara_dos_deputados_fg.ui.notAuthenticated.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.LayoutInflater
import com.fernando.camara_dos_deputados_fg.ui.utils.ActivityViewBinding
import com.fernando.camara_dos_deputados_fg.databinding.ActivityWelcomeBinding

class WelcomeActivity : ActivityViewBinding<ActivityWelcomeBinding>() {
    override fun inflate(layoutInflater: LayoutInflater): ActivityWelcomeBinding {
        return ActivityWelcomeBinding.inflate(layoutInflater)
    }

    override fun onResume() {
        super.onResume()
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        if (!hasNetwork()) {
            val alertDialog = AlertDialog.Builder(this)
                .apply {
                    setTitle("Intenet error")
                    setMessage("Erro ao tentar se conectar a internet")
                    setPositiveButton("Ok") { dialog, item ->
                        finish()
                    }
                }
            alertDialog.create().show()
        }
    }

    private fun hasNetwork(): Boolean {
        val connectionManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectionManager.activeNetwork ?: return false

        val capabilities = connectionManager.getNetworkCapabilities(network) ?: return false
        val transporters : List<Int> = listOf(
            NetworkCapabilities.TRANSPORT_WIFI,
            NetworkCapabilities.TRANSPORT_CELLULAR
        )


        for (transport in transporters) {
            if (capabilities.hasTransport(transport)) {
                return true
            }
        }
        return false

    }


    private fun initListeners() {
        binding.let {
            it.loginButton.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }

            it.registerButton.setOnClickListener {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }
}