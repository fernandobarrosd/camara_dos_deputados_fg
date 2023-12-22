package com.fernando.camara_dos_deputados_fg.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fernando.camara_dos_deputados_fg.R
import com.fernando.camara_dos_deputados_fg.ui.authenticated.activities.PrivateActivity
import com.fernando.camara_dos_deputados_fg.ui.notAuthenticated.activities.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onStart() {
        super.onStart()
        val firebaseAuth = FirebaseAuth.getInstance()


        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this, PrivateActivity::class.java))
            finish()
        }
        else {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }

    }
}