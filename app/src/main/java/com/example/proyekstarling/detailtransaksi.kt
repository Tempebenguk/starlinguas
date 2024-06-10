package com.example.proyekstarling

import java.io.Serializable

class detailtransaksi(
    val harga: Int = 0,
    val nama_menu: String = "",
    val qty: Int = 0
) : Serializable

data class transaksi(
    val catatan: String = "",
    val detail_trx: Map<String, detailtransaksi> = emptyMap(),
    val kembalian: Int = 0,
    val nominal: Int = 0,
    val status: Int = 0,
    val tgl_transaksi: String = "",
    val total_bayar: Int = 0
) : Serializable