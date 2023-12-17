package com.fernando.camara_dos_deputados_fg

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.fernando.camara_dos_deputados_fg.private_screens.PrivateActivity
import com.fernando.camara_dos_deputados_fg.public_screens.WelcomeActivity
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
        }
        else {
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
        finish()
    }
}