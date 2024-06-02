package com.example.proyekstarling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Color
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyekstarling.databinding.DashboardadminBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class dashboardadmin : AppCompatActivity() {
    lateinit var binding: DashboardadminBinding
    private lateinit var database: DatabaseReference
    private lateinit var transactionAdapter: TransactionAdapter
    private val transactionList = mutableListOf<transaksi>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DashboardadminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().reference

        setupRecyclerView()
        fetchTransactions()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        transactionAdapter = TransactionAdapter(transactionList)
        binding.recyclerView.adapter = transactionAdapter
    }

    private fun fetchTransactions() {
        database.child("transaksi").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                transactionList.clear()
                for (dataSnapshot in snapshot.children) {
                    val transaction = dataSnapshot.getValue(transaksi::class.java)
                    transaction?.let { transactionList.add(it) }
                }
                transactionAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("DashboardAdmin", "Failed to read transactions", error.toException())
            }
        })
    }
}