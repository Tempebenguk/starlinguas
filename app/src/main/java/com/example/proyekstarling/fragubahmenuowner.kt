package com.example.proyekstarling

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.proyekstarling.databinding.FrageditmenuownerBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class fragubahmenuowner : Fragment() {
    private lateinit var binding: FrageditmenuownerBinding
    private lateinit var database: DatabaseReference
    private var selectedImageUri: Uri? = null
    private var menuId: String? = null

    private val selectImageResultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            binding.imageView14.setImageURI(uri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FrageditmenuownerBinding.inflate(inflater, container, false)
        val view = binding.root
        database = FirebaseDatabase.getInstance().getReference("menu")

        menuId = arguments?.getString("menuId")
        if (menuId != null) {
            loadMenuData(menuId!!)
        }

        binding.btnUbahMenu.setOnClickListener {
            ubahMenu()
        }

        binding.btnFoto.setOnClickListener {
            selectImageResultLauncher.launch("image/*")
        }

        return view
    }

    private fun loadMenuData(menuId: String) {
        database.child(menuId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val menu = dataSnapshot.getValue(menu::class.java)
                if (menu != null) {
                    binding.ubahIdMenu.setText(menuId)
                    binding.ubahNamaMenu.setText(menu.nama_menu)
                    binding.ubahHargaMenu.setText(menu.harga.toString())
                    binding.ubahStokMenu.setText(menu.stock.toString())
                    Picasso.get().load(menu.gambar_menu).into(binding.imageView14)

                    when (menu.id_kategori) {
                        "kat1" -> binding.ubahradioGroupKategori.check(R.id.radioButtonMakanan)
                        "kat2" -> binding.ubahradioGroupKategori.check(R.id.radioButtonMinuman)
                        "kat3" -> binding.ubahradioGroupKategori.check(R.id.radioButtonSnack)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, "Gagal memuat data menu", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun ubahMenu() {
        val nama = binding.ubahNamaMenu.text.toString().trim()
        val harga = binding.ubahHargaMenu.text.toString().trim()
        val stok = binding.ubahStokMenu.text.toString().toIntOrNull()
        val selectedRadioButtonId = binding.ubahradioGroupKategori.checkedRadioButtonId
        val selectedRadioButton = view?.findViewById<RadioButton>(selectedRadioButtonId)
        val kategori = when (selectedRadioButton?.text.toString()) {
            "Makanan" -> "kat1"
            "Minuman" -> "kat2"
            "Snack" -> "kat3"
            else -> ""
        }

        if (menuId != null) {
            database.child(menuId!!).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val menu = dataSnapshot.getValue(menu::class.java)
                    if (menu != null) {
                        val updatedNama = if (nama.isNotEmpty()) nama else menu.nama_menu
                        val updatedHarga = if (harga.isNotEmpty()) harga.toInt() else menu.harga
                        val updatedStok = stok ?: menu.stock
                        val updatedKategori = if (kategori.isNotEmpty()) kategori else menu.id_kategori

                        if (selectedImageUri != null) {
                            val storageReference = FirebaseStorage.getInstance().reference
                            val imageRef = storageReference.child("menu_images/${menuId}.jpg")
                            imageRef.putFile(selectedImageUri!!).addOnSuccessListener {
                                imageRef.downloadUrl.addOnSuccessListener { uri ->
                                    val updatedGambar = uri.toString()
                                    saveUpdatedMenu(menuId!!, updatedNama, updatedHarga, updatedStok, updatedGambar, updatedKategori)
                                }
                            }.addOnFailureListener {
                                Toast.makeText(context, "Gagal mengunggah gambar", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            val updatedGambar = menu.gambar_menu
                            saveUpdatedMenu(menuId!!, updatedNama, updatedHarga, updatedStok, updatedGambar, updatedKategori)
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(context, "Gagal memuat data menu", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(context, "ID menu tidak valid", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveUpdatedMenu(menuId: String, nama: String, harga: Int, stok: Int, gambar: String, kategori: String) {
        val updatedMenu = menu(
            nama_menu = nama,
            harga = harga,
            stock = stok,
            gambar_menu = gambar,
            id_kategori = kategori
        )

        database.child(menuId).setValue(updatedMenu).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Menu diperbarui", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            } else {
                Toast.makeText(context, "Gagal memperbarui menu", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
