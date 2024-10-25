package com.example.mapapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.Toolbar
import android.graphics.Color



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_main)
        setContentView(R.layout.inicio)

        // Configuración del Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = getString(R.string.app_name)
        toolbar.setTitleTextColor(Color.WHITE)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainScreen)) { v, insets -> //main en mainScreen para la pantalla de LogIn
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean{
        menuInflater.inflate(R.menu.app_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.action_settings ->{
                Toast.makeText(this,"Setting Clicked", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.action_info ->{
                Toast.makeText(this,"Info Clicked", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.action_share ->{
                Toast.makeText(this,"Share Clicked", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.action_search ->{
                Toast.makeText(this,"Search Clicked", Toast.LENGTH_LONG).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}