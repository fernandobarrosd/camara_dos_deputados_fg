package com.fernando.camara_dos_deputados_fg.private_screens

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.fernando.camara_dos_deputados_fg.MainActivity
import com.fernando.camara_dos_deputados_fg.databinding.ActivityPrivateBinding
import com.fernando.camara_dos_deputados_fg.R
import com.google.firebase.auth.FirebaseAuth

class PrivateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrivateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initNavigation()
    }





    private fun initNavigation() {
        val navHostFragment =  supportFragmentManager.findFragmentById(binding.fragmentContainer.id) as NavHostFragment
        val navController = navHostFragment.navController

        setupWithNavController(binding.bottomNavigation, navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val alertDialog = AlertDialog.Builder(this)
            .apply {
                setTitle("Sair da conta")
                    .setMessage("Deseja sair da conta")
                    .setPositiveButton("Sim") { dialog, _ ->
                        val firebaseAuth = FirebaseAuth.getInstance()

                        dialog.dismiss()
                        firebaseAuth.signOut()
                        startActivity(Intent(context, MainActivity::class.java))
                        finish()
                    }
                    .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
            }

        alertDialog.create().show()
        return true
    }
}