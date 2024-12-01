package com.example.mymaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.mymaps.databinding.ActivityCreateMapBinding
import android.util.Log
import com.google.android.gms.maps.model.Marker
import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat
import android.app.Activity
import android.content.Intent
import android.view.Menu
import android.widget.Toast
import com.example.mymaps.models.UserMap
import com.example.mymaps.models.Place



private const val TAG= "CreateMapActivity"
class CreateMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var markers: MutableList<Marker> = mutableListOf()
    private lateinit var binding: ActivityCreateMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = intent.getStringExtra(EXTRA_MAP_TITLE)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mapFragment.view?.let{
            Snackbar.make(it, "Long press to add a marker!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Ok",{})
                .setActionTextColor(ContextCompat.getColor(this, android.R.color.white))
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?):Boolean{
        menuInflater.inflate(R.menu.menu_create_map, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Checj that 'item' is the save menu option
        if(item.itemId == R.id.miSave){
            Log.i(TAG,"Tapped on save!")
            if (markers.isEmpty()){
                Toast.makeText(this,"There must be at least one marker on the map", Toast.LENGTH_LONG).show()
                return true
            }
            val places = markers.map { marker ->
                Place(
                    title = marker.title ?: "Untitled",
                    description = marker.snippet ?: "No description",
                    latitude = marker.position.latitude,
                    longitude = marker.position.longitude
                )
            }
            val mapTitle = intent.getStringExtra(EXTRA_MAP_TITLE) ?: "Untitled Map"
            val userMap = UserMap(mapTitle, places)
            val data = Intent()
            data.putExtra(EXTRA_USER_MAP, userMap)
            setResult(Activity.RESULT_OK, data)
            finish() //Return to MainActivity
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnInfoWindowClickListener{ markerToDelete ->
            Log.i(TAG, "onWindowClickListener- delete this marker")
            markers.remove(markerToDelete)
            markerToDelete.remove()
        }

        mMap.setOnMapLongClickListener{ latLng ->
            Log.i(TAG, "onMapLongClickListener")
            showAlertDialog(latLng)
        }
        // Add a marker in Sydney and move the camera
        val cdmxSur = LatLng(19.3, -99.1)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cdmxSur,10f))
    }

    private fun showAlertDialog(latLng:LatLng){
        val placeFormView = LayoutInflater.from(this).inflate(R.layout.dialog_create_place, null)
        val etTitle = placeFormView.findViewById<EditText>(R.id.etTitle)
        val etDescription = placeFormView.findViewById<EditText>(R.id.etDescription)

        val dialog =
            AlertDialog.Builder(this)
            .setTitle("Create a marker")
            .setView(placeFormView)
            .setNegativeButton("Cancel",null)
            .setPositiveButton("Ok",null)
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener{
            val title = etTitle.text.toString()
            val description = etDescription.text.toString()

            if (title.isEmpty() || description.isEmpty()) {
                // Show mistake if fields are empty
                etTitle.error = "Title is required"
                etDescription.error = "Description is required"
            } else {
                // Make a marker with the values admitted
                val marker = mMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(title)
                        .snippet(description)
                )
                if (marker != null) {
                    markers.add(marker)
                }
                dialog.dismiss() // close dialog
            }
        }
    }
}