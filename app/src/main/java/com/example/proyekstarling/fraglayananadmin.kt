package com.example.proyekstarling

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class fraglayananadmin : Fragment () {
    lateinit var thisParent: dashboardadmin

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thisParent = activity as dashboardadmin
        val view = inflater.inflate(R.layout.fraglayananadmin, container, false)
        val button3: Button = view.findViewById(R.id.button3)

        button3.setOnClickListener {
            val url = "https://wa.me/6282143231811"
            val intentwa = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intentwa)
        }
        return view
    }

    fun onButtonClicked(view: View) {
        val url = "https://wa.me/6282143231811"
        val intentwa = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intentwa)
    }
}