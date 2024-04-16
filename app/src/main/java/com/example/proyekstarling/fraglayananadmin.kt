package com.example.proyekstarling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class fraglayananadmin : Fragment () {
    lateinit var thisParent : dashboardadmin

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thisParent = activity as dashboardadmin
        return inflater.inflate(R.layout.fraglayananadmin, container, false)
    }
}