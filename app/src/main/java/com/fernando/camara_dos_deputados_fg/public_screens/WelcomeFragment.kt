package com.fernando.camara_dos_deputados_fg.public_screens

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fernando.camara_dos_deputados_fg.R
import com.fernando.camara_dos_deputados_fg.databinding.FragmentWelcomeBinding
import com.google.firebase.auth.FirebaseAuth

class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    override fun onStart() {
        super.onStart()

        val firebase = FirebaseAuth.getInstance()

        if (!hasNetwork()) {
            val alertDialog = AlertDialog.Builder(requireContext())
                .apply {
                    setTitle("Intenet error")
                    setMessage("Erro ao tentar se conectar a internet")
                    setPositiveButton("Ok") { dialog, item ->
                        requireActivity().finish()
                    }
                }
            alertDialog.create().show()
        }

        if (firebase.currentUser != null)
            findNavController().navigate(R.id.action_welcomeFragment_to_privateActivity)
    }

    private fun hasNetwork(): Boolean {
        val connectionManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectionManager.activeNetwork ?: return false
        val capabilities = connectionManager.getNetworkCapabilities(network) ?: return false
        val transporters : List<Int> = listOf(
            NetworkCapabilities.TRANSPORT_WIFI,
            NetworkCapabilities.TRANSPORT_CELLULAR
        )


        for (transport in transporters) {
            if (capabilities.hasTransport(transport)) {
                return true
            }
        }
        return false

    }


    private fun initListeners() {
        binding.let {
            it.loginButton.setOnClickListener {
                findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment2)
            }

            it.registerButton.setOnClickListener {
                findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment)
            }
        }
    }
}