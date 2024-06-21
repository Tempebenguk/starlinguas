package com.example.proyekstarling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.proyekstarling.databinding.FragkategoriownerBinding
import com.google.firebase.database.*

class fragkategoriowner : Fragment() {
    private lateinit var binding: FragkategoriownerBinding
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragkategoriownerBinding.inflate(inflater, container, false)
        val view = binding.root
        database = FirebaseDatabase.getInstance().getReference("Admin")

        binding.btnTambahKtg.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragtambahkategoriowner())
                .addToBackStack(null)
                .commit()
        }

        binding.btnCari.setOnClickListener {
            val searchName = binding.cariKtg.text.toString()
            if (searchName.isNotEmpty()) {
                cariAdminBerdasarkanNama(searchName)
            } else {
                ambilDataAdmin()
            }
        }

        ambilDataAdmin()
        return view
    }

    private fun ambilDataAdmin() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.ktgListContainer.removeAllViews()
                for (adminSnapshot in snapshot.children) {
                    val adminId = adminSnapshot.key
                    val admin = adminSnapshot.getValue(admin::class.java)
                    if (admin != null && adminId != null) {
                        admin.id = adminId
                        tambahkanAdminKeView(admin)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun tambahkanAdminKeView(admin: admin) {
        val adminView = LayoutInflater.from(context).inflate(R.layout.user_item, binding.ktgListContainer, false)
        val adminNameTextView = adminView.findViewById<TextView>(R.id.AdminNameTextView)
        val editButton = adminView.findViewById<Button>(R.id.editButtonadm)
        val deleteButton = adminView.findViewById<Button>(R.id.deleteButtonadm)

        adminNameTextView.text = admin.nama

        editButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("adminId", admin.id)
            val fragubahuserowner = fragubahuserowner()
            fragubahuserowner.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragubahuserowner)
                .addToBackStack(null)
                .commit()
        }

        deleteButton.setOnClickListener {
            database.child(admin.id).removeValue().addOnCompleteListener {
                Toast.makeText(context, "Admin dihapus", Toast.LENGTH_SHORT).show()
            }
        }
        binding.ktgListContainer.addView(adminView)
    }

    private fun cariAdminBerdasarkanNama(nama: String) {
        database.orderByChild("nama").equalTo(nama).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.ktgListContainer.removeAllViews()
                for (adminSnapshot in snapshot.children) {
                    val adminId = adminSnapshot.key
                    val admin = adminSnapshot.getValue(admin::class.java)
                    if (admin != null && adminId != null) {
                        admin.id = adminId
                        tambahkanAdminKeView(admin)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Gagal mencari kategori", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
