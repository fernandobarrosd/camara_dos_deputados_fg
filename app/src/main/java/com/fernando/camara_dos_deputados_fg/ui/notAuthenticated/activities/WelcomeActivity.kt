package com.fernando.camara_dos_deputados_fg.ui.notAuthenticated.activities

import android.content.Intent
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