package com.fernando.camara_dos_deputados_fg.ui.authenticated.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import androidx.preference.Preference.OnPreferenceClickListener
import androidx.preference.PreferenceFragmentCompat
import com.fernando.camara_dos_deputados_fg.ui.MainActivity
import com.fernando.camara_dos_deputados_fg.R
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main_settings, rootKey)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    private fun createCurrentAccountEmailPreference(): Preference {

        return Preference(preferenceScreen.context).apply {
            key = "contaAtual"
            title = "Conta atual"
            summary = firebaseAuth.currentUser?.email
            layoutResource = R.layout.layout_preference
        }
    }


    private fun initListeners() {
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
    }
}




