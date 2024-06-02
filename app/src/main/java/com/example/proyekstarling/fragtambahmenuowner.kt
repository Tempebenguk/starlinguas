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
import com.example.proyekstarling.databinding.FragtambahmenuownerBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class fragtambahmenuowner : Fragment() {
    private lateinit var binding: FragtambahmenuownerBinding
    private lateinit var database: DatabaseReference
    private lateinit var storage: StorageReference
    private var selectedImageUri: Uri? = null

    private val selectImageResultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            binding.tambahgambar.setImageURI(uri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragtambahmenuownerBinding.inflate(inflater, container, false)
        val view = binding.root
        database = FirebaseDatabase.getInstance().getReference("menu")
        storage = FirebaseStorage.getInstance().getReference("menu_images")

        binding.btnFoto.setOnClickListener {
            selectImageResultLauncher.launch("image/*")
        }

        binding.btnTambahMenu.setOnClickListener {
            tambahMenu()
        }
        return view
    }

    private fun tambahMenu() {
        val id = binding.tambahIdMenu.text.toString().trim()
        val nama = binding.tambahNamaMenu.text.toString().trim()
        val harga = binding.tambahHargaMenu.text.toString().trim()
        val stok = binding.tambahStokMenu.text.toString().trim()
        val selectedId = binding.radioGroupKategori.checkedRadioButtonId
        val radioButton: RadioButton = binding.root.findViewById(selectedId)
        val kategori = when (radioButton.text.toString()) {
            "Makanan" -> "kat1"
            "Minuman" -> "kat2"
            "Snack" -> "kat3"
            else -> ""
        }

        if (nama.isEmpty() || harga.isEmpty() || stok.isEmpty() || selectedImageUri == null) {
            Toast.makeText(context, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }

        val fileName = UUID.randomUUID().toString()
        val imageRef = storage.child("$fileName.jpg")

        selectedImageUri?.let { uri ->
            imageRef.putFile(uri)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { imageUrl ->
                        val menu = menu(
                            id = id,
                            nama_menu = nama,
                            harga = harga.toInt(),
                            stock = stok.toInt(),
                            gambar_menu = imageUrl.toString(),
                            id_kategori = kategori
                        )

                        database.child(id).setValue(menu).addOnCompleteListener {
                            Toast.makeText(context, "Data Menu telah ditambahkan", Toast.LENGTH_SHORT).show()
                            kosong()
                            requireActivity().supportFragmentManager.popBackStack()
                        }.addOnFailureListener {
                            Toast.makeText(context, "Gagal menambahkan data Menu", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Gagal mengunggah gambar", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun kosong() {
        binding.tambahIdMenu.text.clear()
        binding.tambahNamaMenu.text.clear()
        binding.tambahHargaMenu.text.clear()
        binding.tambahStokMenu.text.clear()
        binding.radioGroupKategori.clearCheck()
        binding.tambahgambar.setImageURI(null)
        selectedImageUri = null
    }
}
