package com.fernando.camara_dos_deputados_fg.public_screens

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.fernando.camara_dos_deputados_fg.ActivityViewBinding
import com.fernando.camara_dos_deputados_fg.constants.Regex
import com.fernando.camara_dos_deputados_fg.databinding.ActivityLoginBinding
import com.fernando.camara_dos_deputados_fg.factories.AlertDialogFactory.Companion.createEmailAndPasswordErrorDialog
import com.fernando.camara_dos_deputados_fg.factories.GoogleSignInClientFactory
import com.fernando.camara_dos_deputados_fg.private_screens.PrivateActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : ActivityViewBinding<ActivityLoginBinding>() {
    private lateinit var googleSignActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var signInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityResultLauncher()
    }
    override fun inflate(layoutInflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth = FirebaseAuth.getInstance()
        signInClient = GoogleSignInClientFactory.createClient(this)
    }

    override fun onResume() {
        super.onResume()
        initListeners()
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
                                    goToPrivateScreens()
                                }
                                .addOnFailureListener {
                                    val loginErrorAlert = AlertDialog.Builder(this).apply {
                                        setTitle("Erro no login")
                                            .setMessage("Credenciais inválidas ou expiradas")
                                            .setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }
                                    }

                                    loginErrorAlert.create().show()
                                }
                        }
                }
            }
    }

    private fun goToPrivateScreens() {
        startActivity(Intent(this, PrivateActivity::class.java))
    }


    private fun initListeners() {
        binding.apply {
            loginWithGoogleButton.setOnClickListener { openGoogleSignIntent() }
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
                        .addOnSuccessListener { goToPrivateScreens() }
                        .addOnFailureListener { emailAndPasswordErrorDialog?.show() }
                        .addOnCompleteListener { binding.loginButton.isClickable = true }
                }
            }
        }
    }

    private fun openGoogleSignIntent() {
        val googleSignInIntent = signInClient.signInIntent
        googleSignActivityResultLauncher.launch(googleSignInIntent)
    }
}