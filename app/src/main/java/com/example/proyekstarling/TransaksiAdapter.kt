package com.example.proyekstarling

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransaksiAdapter(private val transaksiList: List<TransaksiItem>) : RecyclerView.Adapter<TransaksiAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewBulan: TextView = itemView.findViewById(R.id.textViewBulan)
        val textViewTotalTransaksi: TextView = itemView.findViewById(R.id.textViewTotalTransaksi)
        val textViewPendapatan: TextView = itemView.findViewById(R.id.textViewPendapatan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaksi, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaksi = transaksiList[position]
        holder.textViewBulan.text = "Bulan: ${transaksi.bulan}"
        holder.textViewTotalTransaksi.text = "Total Transaksi: ${transaksi.totalTransaksi}"
        holder.textViewPendapatan.text = "Pendapatan: ${transaksi.pendapatan}"
    }

    override fun getItemCount() = transaksiList.size
}

data class TransaksiItem(
    val bulan: String,
    val totalTransaksi: Int,
    val pendapatan: Int
)
