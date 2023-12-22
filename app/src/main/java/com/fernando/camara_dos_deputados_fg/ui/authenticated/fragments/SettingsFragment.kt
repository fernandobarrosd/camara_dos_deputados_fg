package com.fernando.camara_dos_deputados_fg.ui.authenticated.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.preference.Preference
import androidx.preference.Preference.OnPreferenceClickListener
import androidx.preference.PreferenceFragmentCompat
import com.fernando.camara_dos_deputados_fg.ui.MainActivity
import com.fernando.camara_dos_deputados_fg.R
import com.fernando.camara_dos_deputados_fg.factories.GoogleSignInClientFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignActivityResultLauncher: ActivityResultLauncher<Intent>
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main_settings, rootKey)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActivityResultLauncher()
        initFirebaseAuth()
        initCurrentAccountPreference()
        initListeners()

    }

    private fun initCurrentAccountPreference() {
        val currentAccountPreference = createCurrentAccountEmailPreference()
        preferenceScreen.addPreference(currentAccountPreference)
    }

    private fun initFirebaseAuth() {
        firebaseAuth = FirebaseAuth.getInstance()
    }


    private fun initActivityResultLauncher() {
        googleSignActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val resultIntent = result.data
                val resultCode = result.resultCode
                if (resultCode == Activity.RESULT_OK && resultIntent != null) {
                    GoogleSignIn.getSignedInAccountFromIntent(resultIntent)
                        .addOnSuccessListener { account ->
                            val authCredential =
                                GoogleAuthProvider.getCredential(account.idToken, null)
                            firebaseAuth.currentUser?.let { user ->
                                user.reauthenticate(authCredential)
                                    .addOnSuccessListener {
                                        user.reload()
                                    }

                            }
                        }
                }
            }
    }

    private fun createCurrentAccountEmailPreference(): Preference {

        return Preference(preferenceScreen.context).apply {
            key = "contaAtual"
            title = "Conta atual"
            summary = firebaseAuth.currentUser?.email
            layoutResource = R.layout.layout_preference
        }
    }


    private fun initListeners() {
        findPreference<Preference>("updatePassword")
            ?.onPreferenceClickListener = OnPreferenceClickListener {
            return@OnPreferenceClickListener true
        }


        findPreference<Preference>("logout")
            ?.onPreferenceClickListener = OnPreferenceClickListener {
            val alertDialog = AlertDialog.Builder(it.context)
                .apply {
                    setTitle("Sair da conta")
                        .setMessage("Deseja sair da conta")
                        .setPositiveButton("Sim") { dialog, _ ->


                            dialog.dismiss()
                            firebaseAuth.signOut()
                            startActivity(Intent(requireContext(), MainActivity::class.java))
                        }
                        .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
                }

            alertDialog.create().show()
            return@OnPreferenceClickListener true
        }

        findPreference<Preference>("contaAtual")
            ?.onPreferenceClickListener = OnPreferenceClickListener {
            val signInClient = GoogleSignInClientFactory.createClient(it.context)
            val googleSignInIntent = signInClient.signInIntent
            googleSignActivityResultLauncher.launch(googleSignInIntent)
            return@OnPreferenceClickListener true
        }
    }
}




