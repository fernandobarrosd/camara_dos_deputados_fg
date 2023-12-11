package com.fernando.camara_dos_deputados_fg.public_screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.fernando.camara_dos_deputados_fg.R
import com.fernando.camara_dos_deputados_fg.databinding.FragmentRegisterBinding
import com.fernando.camara_dos_deputados_fg.constants.Regex
import com.fernando.camara_dos_deputados_fg.factories.AlertDialogFactory.Companion.createEmailAndPasswordErrorDialog
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var navController: NavController
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        initNavigationController()
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth = FirebaseAuth.getInstance()
    }

    private fun initNavigationController() {
        navController = NavHostFragment.findNavController(this)
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
                                        findNavController().navigate(
                                            R.id.action_registerFragment_to_privateActivity
                                        )
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