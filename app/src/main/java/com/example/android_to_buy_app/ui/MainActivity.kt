package com.example.android_to_buy_app.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.android_to_buy_app.R
import com.example.android_to_buy_app.arch.ToBuyViewModel
import com.example.android_to_buy_app.database.AppDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity: AppCompatActivity() {

    lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Define top-level Fragments
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.customizationFragment))

        // Setup top app bar
        setupActionBarWithNavController(navController, appBarConfiguration)

        val viewModel: ToBuyViewModel by viewModels()
        viewModel.init(AppDatabase.getDatabase(this))

        // Setup bottom nav bar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_menu)
        setupWithNavController(bottomNavigationView, navController)

        // Add our destination change listener to show/hide the bottom nav bar
        val listener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
            if(appBarConfiguration.topLevelDestinations.contains(destination.id)) {
                bottomNavigationView.isVisible = true
            } else {
                bottomNavigationView.isGone = true
            }
        }
        navController.addOnDestinationChangedListener(listener)
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}