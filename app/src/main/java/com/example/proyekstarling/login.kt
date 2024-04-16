package com.example.proyekstarling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.view.View
import com.example.proyekstarling.databinding.LoginBinding

class login : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: LoginBinding
    var owner: String = "owner"
    var admin: String = "admin"

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> login()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener(this)
    }

    private fun login() {
        val username = binding.inpUsername.text.toString()
        val password = binding.inpPassword.text.toString()

        when {
            username == owner && password == owner -> {
                startActivity(Intent(this, dashboardowner::class.java))
                Toast.makeText(this, "Login sebagai Owner", Toast.LENGTH_SHORT).show()
                true
            }
            username == admin && password == admin -> {
                startActivity(Intent(this, dashboardadmin::class.java))
                Toast.makeText(this, "Login sebagai Admin", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                Toast.makeText(this, "Username atau password salah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}