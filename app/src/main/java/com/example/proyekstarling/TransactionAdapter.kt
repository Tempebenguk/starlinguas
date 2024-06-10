package com.example.proyekstarling

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapter(
    private val transactions: MutableList<Pair<String, transaksi>>,
    private val clickListener: (String, transaksi) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val (transactionId, transaction) = transactions[position]
        holder.bind(transactionId, transaction, clickListener)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    fun sortTransactionsByDate(ascending: Boolean) {
        if (ascending) {
            transactions.sortBy { it.second.tgl_transaksi }
        } else {
            transactions.sortByDescending { it.second.tgl_transaksi }
        }
        notifyDataSetChanged()
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewId: TextView = itemView.findViewById(R.id.textViewId)
        private val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        private val textViewTotal: TextView = itemView.findViewById(R.id.textViewTotal)

        fun bind(transactionId: String, transaction: transaksi, clickListener: (String, transaksi) -> Unit) {
            textViewId.text = transactionId
            textViewDate.text = transaction.tgl_transaksi
            textViewTotal.text = "Total: Rp. ${transaction.total_bayar}"

            itemView.setOnClickListener {
                clickListener(transactionId, transaction)
            }
        }
    }
}
