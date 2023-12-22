package com.fernando.camara_dos_deputados_fg.ui.authenticated.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.fernando.camara_dos_deputados_fg.ActivityViewBinding
import com.fernando.camara_dos_deputados_fg.databinding.ActivityPrivateBinding
import com.fernando.camara_dos_deputados_fg.R
import com.fernando.camara_dos_deputados_fg.ui.authenticated.fragments.DeputadosFragment
import com.fernando.camara_dos_deputados_fg.ui.authenticated.fragments.HomeFragment
import com.fernando.camara_dos_deputados_fg.ui.authenticated.fragments.ProfileFragment
import com.fernando.camara_dos_deputados_fg.ui.authenticated.fragments.SettingsFragment
import com.fernando.camara_dos_deputados_fg.ui.notAuthenticated.activities.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth

class PrivateActivity : ActivityViewBinding<ActivityPrivateBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()
        initFirstScreen()
    }


    override fun onResume() {
        super.onResume()
        initListeners()
    }

    override fun inflate(layoutInflater: LayoutInflater): ActivityPrivateBinding {
        return ActivityPrivateBinding.inflate(layoutInflater)
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
                        goToWelcomeScreen()


                    }
                    .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
            }

        alertDialog.create().show()
        return true
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun goToWelcomeScreen() {
        startActivity(Intent(this, WelcomeActivity::class.java))
    }

    private fun initFirstScreen() {
        supportFragmentManager.commit {
            replace(binding.fragmentContainer.id, HomeFragment())
        }
    }

    private fun initListeners() {
        binding.bottomNavigation.setOnItemSelectedListener {menuItem ->
            val itemId = menuItem.itemId
            var fragment : Fragment? = null

            if (itemId == R.id.homeFragment) {
                fragment = HomeFragment()
            }
            else if (itemId == R.id.profileFragment) {
                fragment = ProfileFragment()
            }
            else if (itemId == R.id.settingsFragment) {
                fragment = SettingsFragment()
            }
            else if (itemId == R.id.deputadosFragment) {
                fragment = DeputadosFragment()
            }

            supportFragmentManager.commit {
                if (fragment != null) {
                    replace(binding.fragmentContainer.id, fragment)
                }
            }
            return@setOnItemSelectedListener true
        }
    }


}