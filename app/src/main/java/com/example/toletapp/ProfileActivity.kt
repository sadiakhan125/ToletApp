package com.example.toletapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileImage: ImageView
    private lateinit var tvEmail: TextView
    private lateinit var tvBalance: TextView
    private lateinit var btnAddService: Button
    private lateinit var btnLogout: Button

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile) // Make sure this matches your XML filename

        profileImage = findViewById(R.id.profileImage)
        tvEmail = findViewById(R.id.tvEmail)
        tvBalance = findViewById(R.id.tvBalance)
        btnAddService = findViewById(R.id.btnAddService)
        btnLogout = findViewById(R.id.btnLogout)

        // Show logged-in user email or default text
        val currentUser = auth.currentUser
        tvEmail.text = currentUser?.email ?: "No Email"

        // Set dummy balance or fetch from DB if you want
        tvBalance.text = "0.00 BDT"

        // Add Property button click: open HomeRentService activity
        btnAddService.setOnClickListener {
            val intent = Intent(this, HomeRentService::class.java)
            startActivity(intent)
        }

        // Logout button click: sign out and go to login page
        btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, loginpage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
