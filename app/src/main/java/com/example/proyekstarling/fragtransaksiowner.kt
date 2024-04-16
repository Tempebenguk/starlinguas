package com.example.proyekstarling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class fragtransaksiowner : Fragment () {
    lateinit var thisParent : dashboardowner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thisParent = activity as dashboardowner
        return inflater.inflate(R.layout.fragtransaksiowner, container, false)
    }
}