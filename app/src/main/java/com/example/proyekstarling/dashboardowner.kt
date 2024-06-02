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
import fraglayananowner

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

        fraguser = fraguserowner()
        binding.bnv1.setOnNavigationItemSelectedListener(this)
        fragmenu = fragmenuowner()
        fragtransaksi = fragtransaksiowner()
        fraglayanan = fraglayananowner()
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
