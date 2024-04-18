package com.example.proyekstarling

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.proyekstarling.databinding.FragmenuownerBinding

class fragmenuowner : Fragment () {
    lateinit var thisParent: dashboardowner
    lateinit var binding: FragmenuownerBinding
    lateinit var adapterLv: ArrayAdapter<String>
    lateinit var arrGabungan: ArrayList<String>
    var selectedPosition: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmenuownerBinding.inflate(inflater, container, false)
        val view = binding.root
        thisParent = activity as dashboardowner

        // Inisialisasi arrGabungan di sini
        arrGabungan = ArrayList()

        val Kategori = arrayOf("Makanan", "Minuman")
        adapterLv = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arrGabungan)
        binding.lvadmin.adapter = adapterLv
        binding.lvadmin.choiceMode = ListView.CHOICE_MODE_NONE
        binding.lvadmin.setOnItemLongClickListener { parent, view, position, id ->
            selectedPosition = position
            showPopupMenu(view)
            true
        }
        binding.buttonSimpan.setOnClickListener {
            val idMenu = binding.editTextIdMenu.text.toString()
            val namaMenu = binding.editTextNamaMenu.text.toString()
            val stok = binding.editTextStok.text.toString()
            val kategori = binding.autoCompleteKategori.text.toString()

            if (idMenu in arrGabungan.map { it.split("\n")[0].split(": ")[1] }) {
                Toast.makeText(requireContext(), "ID sudah ada", Toast.LENGTH_SHORT).show()
            } else {
                val menuData = "Id_Menu: $idMenu\nNama Menu: $namaMenu\nStok: $stok\nKategori: $kategori"
                arrGabungan.add(menuData)
                adapterLv.notifyDataSetChanged()
                clearForm()

                Toast.makeText(requireContext(), "Berhasil menambahkan data menu", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttongambar.setOnClickListener {
            val intentGaleri = Intent()
            intentGaleri.type = "image/*"
            intentGaleri.action = Intent.ACTION_GET_CONTENT
            startActivity(Intent.createChooser(intentGaleri, "Pilih Gambar ..."))
        }

        val adapterActv = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, Kategori)
        binding.autoCompleteKategori.setAdapter(adapterActv)

        return view
    }

    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.inflate(R.menu.menu_popup)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.popupEdit -> {
                    editItem(selectedPosition)
                    true
                }
                R.id.popupDelete -> {
                    deleteItem(selectedPosition)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun editItem(position: Int) {
        // Implement edit item logic here
    }

    private fun deleteItem(position: Int) {
        arrGabungan.removeAt(position)
        adapterLv.notifyDataSetChanged()
        Toast.makeText(requireContext(), "Data dihapus", Toast.LENGTH_SHORT).show()
    }

    private fun clearForm() {
        binding.editTextIdMenu.setText("")
        binding.editTextNamaMenu.setText("")
        binding.editTextStok.setText("")
        binding.autoCompleteKategori.setText("")
    }
}
