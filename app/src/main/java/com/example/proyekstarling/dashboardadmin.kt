package com.example.proyekstarling

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyekstarling.databinding.DashboardadminBinding
import com.google.firebase.database.*

class dashboardadmin : AppCompatActivity() {
    lateinit var binding: DashboardadminBinding
    private lateinit var database: DatabaseReference
    private lateinit var transactionAdapter: TransactionAdapter
    private val transactionList = mutableListOf<Pair<String, transaksi>>()

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
        transactionAdapter = TransactionAdapter(transactionList) { transactionId, transaction ->
            val fragment = fragtransaksiadmin.newInstance(transactionId, transaction)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        binding.recyclerView.adapter = transactionAdapter
    }

    private fun fetchTransactions() {
        database.child("transaksi").orderByChild("tgl_transaksi")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    transactionList.clear()
                    snapshot.children.reversed().forEach { dataSnapshot ->
                        val transaction = dataSnapshot.getValue(transaksi::class.java)
                        val transactionId = dataSnapshot.key ?: ""
                        transaction?.let { transactionList.add(transactionId to it) }
                    }
                    transactionAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("DashboardAdmin", "Failed to read transactions", error.toException())
                }
            })
    }
}
