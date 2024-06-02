package com.example.proyekstarling
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekstarling.detailtransaksi
import com.example.proyekstarling.R

class TransactionAdapter(private val transactions: List<transaksi>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.bind(transaction)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        private val textViewTotal: TextView = itemView.findViewById(R.id.textViewTotal)
        private val detailsContainer: ViewGroup = itemView.findViewById(R.id.detailsContainer)

        fun bind(transaction: transaksi) {
            textViewDate.text = transaction.tgl_transaksi
            textViewTotal.text = "Total: ${transaction.total_bayar}"

            detailsContainer.removeAllViews()
            for ((_, detail) in transaction.detail_trx) {
                val detailView = TextView(itemView.context).apply {
                    text = "${detail.nama_menu} - ${detail.qty} x ${detail.harga}"
                }
                detailsContainer.addView(detailView)
            }
        }
    }
}
