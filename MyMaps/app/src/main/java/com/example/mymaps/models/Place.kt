package com.example.mymaps.models

import java.io.Serializable


// Attributes for each place/location
data class Place(val title: String, val description: String, val latitude: Double, val longitude:Double) : Serializable