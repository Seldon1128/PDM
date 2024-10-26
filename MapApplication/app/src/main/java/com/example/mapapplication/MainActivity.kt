package com.example.mapapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.graphics.Color

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_main)
        setContentView(R.layout.inicio)

        // Inicialización de elementos
        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.navView)
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        // Configuración del Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = getString(R.string.app_name)
        toolbar.setTitleTextColor(Color.WHITE)

        // Configuración del Navigation Drawer
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_settings -> {
                    Toast.makeText(this, "Setting Clicked", Toast.LENGTH_LONG).show()
                }
                R.id.action_info -> {
                    Toast.makeText(this, "Info Clicked", Toast.LENGTH_LONG).show()
                }
                R.id.action_share -> {
                    Toast.makeText(this, "Share Clicked", Toast.LENGTH_LONG).show()
                }
                R.id.action_search -> {
                    Toast.makeText(this, "Search Clicked", Toast.LENGTH_LONG).show()
                }
            }
            drawerLayout.closeDrawers() // Cierra el Drawer al seleccionar una opción
            true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainScreen)) { v, insets -> //main en mainScreen para la pantalla de LogIn
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
