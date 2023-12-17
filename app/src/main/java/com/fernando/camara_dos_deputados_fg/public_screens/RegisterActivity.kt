package com.fernando.camara_dos_deputados_fg.public_screens

import android.view.LayoutInflater
import com.fernando.camara_dos_deputados_fg.ActivityViewBinding
import com.fernando.camara_dos_deputados_fg.constants.Regex
import com.fernando.camara_dos_deputados_fg.databinding.ActivityRegisterBinding
import com.fernando.camara_dos_deputados_fg.factories.AlertDialogFactory.Companion.createEmailAndPasswordErrorDialog
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : ActivityViewBinding<ActivityRegisterBinding>() {
    private lateinit var firebaseAuth: FirebaseAuth


    override fun inflate(layoutInflater: LayoutInflater): ActivityRegisterBinding {
        return ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth = FirebaseAuth.getInstance()
    }



    private fun initListeners() {
        binding.apply {
            registerButton.setOnClickListener { view ->
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()
                val emailAndPasswordErrorDialog =
                    createEmailAndPasswordErrorDialog(view.context)

                if (email.isEmpty() || password.isEmpty()) {
                    emailAndPasswordErrorDialog!!.show()
                } else if (!email.matches(Regex.EMAIL)) {
                    emailAndPasswordErrorDialog!!.show()
                } else {
                    registerButton.isClickable = false
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener { authResult ->
                            if (authResult.credential != null) {
                                firebaseAuth.signInWithCredential(authResult.credential!!)
                                    .addOnSuccessListener {

                                    }
                            }
                        }
                        .addOnFailureListener {
                            emailAndPasswordErrorDialog!!.show()
                        }
                        .addOnCompleteListener {
                            binding.registerButton.isClickable = true
                        }
                }
            }
        }

    }
}