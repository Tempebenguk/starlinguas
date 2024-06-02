package com.example.proyekstarling

class detailtransaksi(
    val harga: Int = 0,
    val nama_menu: String = "",
    val qty: Int = 0
)

data class transaksi(
    val detail_trx: Map<String, detailtransaksi> = emptyMap(),
    val status: Int = 0,
    val tgl_transaksi: String = "",
    val total_bayar: Int = 0,
    val nominal: Int = 0,
    val kembalian: Int = 0
)