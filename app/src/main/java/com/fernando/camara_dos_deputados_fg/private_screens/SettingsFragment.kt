package com.fernando.camara_dos_deputados_fg.private_screens

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.Preference.OnPreferenceClickListener
import androidx.preference.PreferenceFragmentCompat
import com.fernando.camara_dos_deputados_fg.MainActivity
import com.fernando.camara_dos_deputados_fg.R
import com.fernando.camara_dos_deputados_fg.databinding.FragmentSettingsBinding
import com.google.firebase.auth.FirebaseAuth


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerView.id, PreferencesFragment())
            .commit()
    }

    class PreferencesFragment :  PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.main_settings, rootKey)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            initListeners()
            val currentAccountPreference = createCurrentAccountEmailPreference()
            preferenceScreen.addPreference(currentAccountPreference)

        }

        private fun createCurrentAccountEmailPreference() : Preference {
            val firebaseAuth = FirebaseAuth.getInstance()

            return Preference(preferenceScreen.context).apply {
                key = "contaAtual"
                title = "Conta atual"
                summary = firebaseAuth.currentUser?.email
            }
        }



        private fun initListeners() {
            findPreference<Preference>("updatePassword")
                ?.onPreferenceClickListener = OnPreferenceClickListener {
                val action = SettingsFragmentDirections.actionSettingsFragmentToUpdateActivity("password")
                findNavController().navigate(action)
                return@OnPreferenceClickListener true
            }

            findPreference<Preference>("updateEmail")
                ?.onPreferenceClickListener = OnPreferenceClickListener {
                val action = SettingsFragmentDirections.actionSettingsFragmentToUpdateActivity("email")
                findNavController().navigate(action)
                return@OnPreferenceClickListener true
            }

            findPreference<Preference>("logout")
                ?.onPreferenceClickListener = OnPreferenceClickListener {
                val alertDialog = AlertDialog.Builder(it.context)
                    .apply {
                        setTitle("Sair da conta")
                            .setMessage("Deseja sair da conta")
                            .setPositiveButton("Sim") { dialog, _ ->
                                val firebase = FirebaseAuth.getInstance()

                                dialog.dismiss()
                                firebase.signOut()
                                startActivity(Intent(requireContext(), MainActivity::class.java))
                            }
                            .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
                    }

                alertDialog.create().show()
                return@OnPreferenceClickListener true
            }

            findPreference<Preference>("contaAtual")
                ?.onPreferenceClickListener = OnPreferenceClickListener {
                    return@OnPreferenceClickListener true
            }
        }



    }
}