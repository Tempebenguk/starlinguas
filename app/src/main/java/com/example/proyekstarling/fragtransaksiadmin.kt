package com.example.proyekstarling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.proyekstarling.databinding.FragtransaksiadminBinding
import com.google.firebase.database.FirebaseDatabase

class fragtransaksiadmin : Fragment() {

    private lateinit var binding: FragtransaksiadminBinding
    private var transaksiId: String? = null

    companion object {
        private const val ARG_TRANSACTION_ID = "transactionId"
        private const val ARG_TRANSACTION = "transaction"

        fun newInstance(transactionId: String, transaction: transaksi): fragtransaksiadmin {
            val fragment = fragtransaksiadmin()
            val args = Bundle()
            args.putString(ARG_TRANSACTION_ID, transactionId)
            args.putSerializable(ARG_TRANSACTION, transaction)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragtransaksiadminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transaksiId = arguments?.getString(ARG_TRANSACTION_ID)
        val transaction = arguments?.getSerializable(ARG_TRANSACTION) as? transaksi
        binding.ID.text = transaksiId

        transaction?.let {
            tampilkanDetailTransaksi(transaksiId ?: "", it)
            setupButtonVisibility(it.status)
        }
    }

    private fun tampilkanDetailTransaksi(transactionId: String, transaction: transaksi) {
        val detailText = StringBuilder("Halo Admin,\n\nTerdapat pesanan untuk transaksi id $transactionId. Berikut detail pesanan:\n\n")

        for ((_, detail) in transaction.detail_trx) {
            detailText.append("${detail.nama_menu} (${detail.qty} x Rp. ${detail.harga}) = Rp. ${detail.qty * detail.harga}\n")
        }

        detailText.append("\nTotal Pembayaran untuk transaksi $transactionId:\nRp. ${transaction.total_bayar}\n")
        detailText.append("Nominal yang dibayarkan: Rp. ${transaction.nominal}\n")
        detailText.append("Kembalian: Rp. ${transaction.kembalian}\n")
        detailText.append("Catatan : ${transaction.catatan}\n\n")

        if (transaction.status == 2) {
            detailText.append("Transaksi ini sudah dikonfirmasi.")
        } else {
            detailText.append("Silahkan tekan Ya untuk konfirmasi pemesanan.")
        }

        binding.textViewTransactionDetails.text = detailText.toString()
    }

    private fun setupButtonVisibility(status: Int) {
        if (status == 1) {
            binding.confirmButton.visibility = View.VISIBLE
            binding.confirmButton.setOnClickListener {
                updateStatusTransaksi(transaksiId ?: "")
            }
        } else {
            binding.confirmButton.visibility = View.GONE
        }
    }

    private fun updateStatusTransaksi(transactionId: String) {
        val databaseReference = FirebaseDatabase.getInstance().reference.child("transaksi").child(transactionId)
        databaseReference.child("status").setValue(2)
            .addOnSuccessListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
            .addOnFailureListener {
            }
    }
}
