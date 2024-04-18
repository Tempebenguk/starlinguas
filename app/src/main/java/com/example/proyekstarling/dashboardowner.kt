package com.example.proyekstarling

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.example.proyekstarling.databinding.DashboardownerBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import fraglayananowner
import fraguserowner


class dashboardowner : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: DashboardownerBinding
    lateinit var fraguser : fraguserowner
    lateinit var fragmenu : fragmenuowner
    lateinit var fragtransaksi : fragtransaksiowner
    lateinit var fraglayanan : fraglayananowner
    lateinit var ft : FragmentTransaction


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DashboardownerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bnv1.setOnNavigationItemSelectedListener(this)
        fraguser = fraguserowner()
        fragmenu = fragmenuowner()
        fragtransaksi = fragtransaksiowner()
        fraglayanan = fraglayananowner()

//        val receivedData = intent?.getStringExtra("keyData")
//        if (receivedData != null) {
//            // Lakukan sesuatu dengan data yang diterima
//        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.itemuser-> {
                ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.fragmentLayout, fraguser).commit()
                binding.fragmentLayout.setBackgroundColor(Color.WHITE)
                binding.fragmentLayout.visibility = View.VISIBLE

            }
            R.id.itemmenu-> {
                ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.fragmentLayout, fragmenu).commit()
                binding.fragmentLayout.setBackgroundColor(Color.WHITE)
                binding.fragmentLayout.visibility = View.VISIBLE
            }
            R.id.itemtransaksi-> {
                ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.fragmentLayout, fragtransaksi).commit()
                binding.fragmentLayout.setBackgroundColor(Color.WHITE)
                binding.fragmentLayout.visibility = View.VISIBLE
            }
            R.id.itemlayanan-> {
                ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.fragmentLayout, fraglayanan).commit()
                binding.fragmentLayout.setBackgroundColor(Color.WHITE)
                binding.fragmentLayout.visibility = View.VISIBLE
            }
            R.id.itemhome -> binding.fragmentLayout.visibility = View.GONE
        }
        return true
    }
}