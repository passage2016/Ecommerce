package com.example.ecommerce.view

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.example.ecommerce.R
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.ecommerce.model.remote.UserVolleyHandler
import com.example.ecommerce.presenter.login.LoginMVP
import com.example.ecommerce.presenter.login.LoginPresenter
import com.example.ecommerce.presenter.logout.LogoutMVP
import com.example.ecommerce.presenter.logout.LogoutPresenter
import com.google.android.material.navigation.NavigationView
import com.google.android.material.progressindicator.CircularProgressIndicator


class MainActivity : AppCompatActivity(), LogoutMVP.LogoutView {

    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var drawerLayout: DrawerLayout
    lateinit var presenter: LogoutPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        sharedPreferences = getSharedPreferences(LoginActivity.ACCOUNT_INFO_FILE_NAME, MODE_PRIVATE)
        editor = sharedPreferences.edit()
        presenter = LogoutPresenter(UserVolleyHandler(this), this)

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView

        navigationView.setNavigationItemSelectedListener{ menuItem->
            when(menuItem.itemId){
                R.id.mi_logout->{
                    val email = sharedPreferences.getString(LoginActivity.EMAIL, "-1")
                    email?.let {
                        presenter.logoutUser(email)
                    }

                }
                else -> {
                    menuItem.onNavDestinationSelected(findNavController(R.id.my_nav_host_fragment))
                            || super.onOptionsItemSelected(menuItem)
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
            true

        }


        val navView = navigationView.inflateHeaderView(R.layout.nav_view_header);

        val tvNavHeaderName: TextView = navView.findViewById(R.id.tv_nav_header_name)
        val tvNavHeaderEmail: TextView = navView.findViewById(R.id.tv_nav_header_email)
        val tvNavHeaderPhone: TextView = navView.findViewById(R.id.tv_nav_header_phone)


        tvNavHeaderName.text =sharedPreferences.getString(LoginActivity.NAME, LoginActivity.NAME)
        tvNavHeaderEmail.text =sharedPreferences.getString(LoginActivity.EMAIL, LoginActivity.EMAIL)
        tvNavHeaderPhone.text =sharedPreferences.getString(LoginActivity.PHONE, LoginActivity.PHONE)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        val navController = host.navController
        val drawerLayout: DrawerLayout? = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home_dest, R.id.cart_dest, R.id.order_dest),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }





    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.e("onBackPressed", "${item.itemId}")
        val current: Fragment? = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment)
        current?.let {
            Log.e("Fragment", "${it.id}")
        }
        if (item.itemId == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)

    }



    override fun onSupportNavigateUp(): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        return findNavController(R.id.my_nav_host_fragment).navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun setResult(message: String) {
        val intent: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onLoad(isLoading: Boolean) {
        if(isLoading){
            findViewById<CircularProgressIndicator>(R.id.circularProgressBar)?.visibility = View.VISIBLE
        } else {
            findViewById<CircularProgressIndicator>(R.id.circularProgressBar)?.visibility = View.GONE
        }
    }

}