package com.example.proyekstarling

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.FragmentTransaction
import com.example.proyekstarling.databinding.DashboardownerBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import fraglayananowner

class dashboardowner : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: DashboardownerBinding
    lateinit var fraguser : fraguserowner
    lateinit var fragmenu : fragmenuowner
    lateinit var fragtransaksi : fragtransaksiowner
    lateinit var fraglayanan : fraglayananowner
    lateinit var fragaboutowner: fragaboutowner
    lateinit var ft : FragmentTransaction
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DashboardownerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fraguser = fraguserowner()
        binding.bnv1.setOnNavigationItemSelectedListener(this)
        fragmenu = fragmenuowner()
        fragtransaksi = fragtransaksiowner()
        fraglayanan = fraglayananowner()
        fragaboutowner = fragaboutowner()
        database = FirebaseDatabase.getInstance().reference

        // Menampilkan jumlah total data user
        database.child("Admin").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userCount = snapshot.childrenCount
                binding.userCountTextView.text = "Total Data Admin : $userCount"
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

        // Menampilkan jumlah total data menu
        database.child("menu").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val menuCount = snapshot.childrenCount
                binding.menuCountTextView.text = "Total Data Menu : $menuCount"
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

        // Menampilkan jumlah total data transaksi
        database.child("transaksi").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val transactionCount = snapshot.childrenCount
                binding.transactionCountTextView.text = "Total Data Transaksi : $transactionCount"
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.itemuser-> {
                ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.fragmentLayout, fraguser).commit()
                binding.fragmentLayout.setBackgroundColor(Color.argb(255,255,255,255))
                binding.fragmentLayout.visibility = View.VISIBLE
            }
            R.id.itemmenu-> {
                ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.fragmentLayout, fragmenu).commit()
                binding.fragmentLayout.setBackgroundColor(Color.argb(255,255,255,255))
                binding.fragmentLayout.visibility = View.VISIBLE
            }
            R.id.itemtransaksi-> {
                ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.fragmentLayout, fragtransaksi).commit()
                binding.fragmentLayout.setBackgroundColor(Color.argb(255,255,255,255))
                binding.fragmentLayout.visibility = View.VISIBLE
            }
            R.id.itemAbout-> {
                ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.fragmentLayout, fragaboutowner).commit()
                binding.fragmentLayout.setBackgroundColor(Color.argb(255,255,255,255))
                binding.fragmentLayout.visibility = View.VISIBLE
            }
            R.id.itemrekap-> {
                ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.fragmentLayout, fraglayanan).commit()
                binding.fragmentLayout.setBackgroundColor(Color.argb(255,255,255,255))
                binding.fragmentLayout.visibility = View.VISIBLE
            }
            R.id.itemhome -> binding.fragmentLayout.visibility = View.GONE
        }
        return true
    }
}
