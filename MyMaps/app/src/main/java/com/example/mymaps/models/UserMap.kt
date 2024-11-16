package com.example.mymaps.models

import java.io.Serializable

// Attributes of the user map
data class UserMap(val title: String, val places: List<Place>) : Serializable