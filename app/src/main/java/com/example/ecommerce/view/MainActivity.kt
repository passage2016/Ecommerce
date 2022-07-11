package com.example.ecommerce.view

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.window.SplashScreen
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.ecommerce.R
import com.example.ecommerce.view.home.SubCategoryFragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var appBarConfiguration:AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        val navController = host.navController
//
        val drawerLayout : DrawerLayout? = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home_dest, R.id.cart_dest, R.id.order_dest, R.id.address_dest),
            drawerLayout)
//
        setupActionBar(navController, appBarConfiguration)
//注释
//        setupNavigationMenu(navController)
//
//
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            val dest: String = try {
//                resources.getResourceName(destination.id)
//            } catch (e: Resources.NotFoundException) {
//                Integer.toString(destination.id)
//            }
//
//            Toast.makeText(this@MainActivity, "Navigated to $dest",
//                Toast.LENGTH_SHORT).show()
//            Log.d("NavigationActivity", "Navigated to $dest")
//        }
    }
//
//    private fun setupNavigationMenu(navController: NavController) {
//
//        val sideNavView = findViewById<NavigationView>(R.id.nav_view)
//        sideNavView?.setupWithNavController(navController)
//    }
//
//
//        override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        val retValue = super.onCreateOptionsMenu(menu)
//        val navigationView = findViewById<NavigationView>(R.id.nav_view)
//        if (navigationView == null) {
//            menuInflater.inflate(R.menu.menu_drawer_nav, menu)
//            return true
//        }
//        return retValue
//    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_drawer_nav, menu)
        return true
    }

    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfig)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.mi_logout){

            val intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            return true
        } else {
            return item.onNavDestinationSelected(findNavController(R.id.my_nav_host_fragment))
                    || super.onOptionsItemSelected(item)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.my_nav_host_fragment).navigateUp(appBarConfiguration)
    }


}