package com.example.proyekstarling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.proyekstarling.databinding.FrageditkategoriownerBinding
import com.example.proyekstarling.databinding.FragedituserownerBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class fragubahkategoriowner : Fragment() {
    private lateinit var binding: FrageditkategoriownerBinding
    private lateinit var database: DatabaseReference
    private var adminId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FrageditkategoriownerBinding.inflate(inflater, container, false)
        val view = binding.root
        database = FirebaseDatabase.getInstance().getReference("Admin")

        // Get adminId from arguments
        adminId = arguments?.getString("adminId")
        if (adminId != null) {
            loadAdminData(adminId!!)
        }

        return view
    }

    private fun loadAdminData(adminId: String) {
        database.child(adminId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val admin = dataSnapshot.getValue(admin::class.java)
                if (admin != null) {

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
