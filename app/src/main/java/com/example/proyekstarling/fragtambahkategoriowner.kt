package com.example.proyekstarling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.proyekstarling.admin
import com.example.proyekstarling.databinding.FragtambahkategoriownerBinding

class fragtambahkategoriowner : Fragment() {
    private lateinit var binding: FragtambahkategoriownerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragtambahkategoriownerBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

}