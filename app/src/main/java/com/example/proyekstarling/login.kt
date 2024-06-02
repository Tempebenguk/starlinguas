package com.example.proyekstarling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.view.View
import com.example.proyekstarling.databinding.LoginBinding
import com.example.proyekstarling.User
import com.google.firebase.database.*

class login : AppCompatActivity(), View.OnClickListener {
    private lateinit var database: FirebaseDatabase
    private lateinit var binding: LoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogin.setOnClickListener(this)

        database = FirebaseDatabase.getInstance()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> login()
        }
    }

    private fun login() {
        val username = binding.inpUsername.text.toString()
        val password = binding.inpPassword.text.toString()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }

        checkUser("Admin", username, password)
    }

    private fun checkUser(userType: String, username: String, password: String) {
        val reference = database.getReference(userType)

        reference.child(username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("LoginActivity", "User $userType ditemukan")
                    val user = dataSnapshot.getValue(User::class.java)
                    Log.d("LoginActivity", "Stored password: ${user?.password}")
                    if (user?.password == password) {
                        val intent = if (userType == "Admin") {
                            Intent(this@login, dashboardadmin::class.java)
                        } else {
                            Intent(this@login, dashboardowner::class.java)
                        }
                        startActivity(intent)
                        Toast.makeText(this@login, "Login sebagai $userType", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@login, "Password salah", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("LoginActivity", "User $userType tidak ditemukan, mencoba owner")
                    if (userType == "Admin") {
                        checkUser("owner", username, password)
                    } else {
                        Toast.makeText(this@login, "Username tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@login, "Database error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
