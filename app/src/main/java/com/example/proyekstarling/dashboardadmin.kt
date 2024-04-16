package com.example.proyekstarling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Color
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.example.proyekstarling.databinding.DashboardadminBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class dashboardadmin : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: DashboardadminBinding
    lateinit var fragtransaksi : fragtransaksiadmin
    lateinit var fraglayanan : fraglayananadmin
    lateinit var ft : FragmentTransaction


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DashboardadminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bnv1.setOnNavigationItemSelectedListener(this)
        fragtransaksi = fragtransaksiadmin()
        fraglayanan = fraglayananadmin()
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.itemtransaksi-> {
                ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.fragmentLayout, fragtransaksi).commit()
                binding.fragmentLayout.setBackgroundColor(Color.argb(255,255,255,255))
                binding.fragmentLayout.visibility = View.VISIBLE
            }
            R.id.itemlayanan-> {
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