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
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat


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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
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
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
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