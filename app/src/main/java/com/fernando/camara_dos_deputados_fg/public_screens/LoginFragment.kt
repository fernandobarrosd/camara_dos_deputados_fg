package com.fernando.camara_dos_deputados_fg.public_screens

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fernando.camara_dos_deputados_fg.R
import com.fernando.camara_dos_deputados_fg.constants.Regex
import com.fernando.camara_dos_deputados_fg.databinding.FragmentLoginBinding
import com.fernando.camara_dos_deputados_fg.factories.AlertDialogFactory.Companion.createEmailAndPasswordErrorDialog
import com.fernando.camara_dos_deputados_fg.factories.GoogleSignInClientFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginFragment : Fragment() {
    private lateinit var googleSignActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var signInClient: GoogleSignInClient
    private lateinit var binding: FragmentLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        initActivityResultLauncher()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth = FirebaseAuth.getInstance()
        signInClient = GoogleSignInClientFactory.createClient(requireContext())
    }

    private fun initActivityResultLauncher() {
        googleSignActivityResultLauncher =
            registerForActivityResult(StartActivityForResult()) { result ->
                val resultIntent = result.data
                val resultCode = result.resultCode
                if (resultCode == Activity.RESULT_OK && resultIntent != null) {
                    GoogleSignIn.getSignedInAccountFromIntent(resultIntent)
                        .addOnSuccessListener { account ->
                            val authCredential =
                                GoogleAuthProvider.getCredential(account.idToken, null)
                            firebaseAuth.signInWithCredential(authCredential)
                                .addOnSuccessListener {
                                    findNavController().navigate(
                                        R.id.action_loginFragment2_to_privateActivity
                                    )
                                }
                                .addOnFailureListener {
                                    println(it.message)
                                }
                        }
                }
            }
    }


    private fun initListeners() {
        binding.apply {
            loginWithGoogleButton.setOnClickListener {
                openGoogleSignIntent() }
            loginButton.setOnClickListener { view ->
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()
                val emailAndPasswordErrorDialog = createEmailAndPasswordErrorDialog(view.context)
                if (email.isEmpty() || password.isEmpty()) {
                    emailAndPasswordErrorDialog!!.show()
                } else if (!email.matches(Regex.EMAIL)) {
                    emailAndPasswordErrorDialog!!.show()
                } else {
                    loginButton.isClickable = false
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            findNavController()
                                .navigate(R.id.action_loginFragment2_to_privateActivity)
                        }
                        .addOnFailureListener {
                            emailAndPasswordErrorDialog?.show()
                        }
                        .addOnCompleteListener {
                            binding.loginButton.isClickable = true
                        }
                }
            }
        }
    }

    private fun openGoogleSignIntent() {
        val googleSignInIntent = signInClient.signInIntent
        googleSignActivityResultLauncher.launch(googleSignInIntent)
    }
}