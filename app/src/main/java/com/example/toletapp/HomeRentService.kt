package com.example.toletapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class HomeRentService : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance("https://toletapp-35d17-default-rtdb.firebaseio.com")

    private lateinit var etHomeTitle: EditText
    private lateinit var etAddress: EditText
    private lateinit var etRentPrice: EditText
    private lateinit var etBedrooms: EditText
    private lateinit var etContact: EditText
    private lateinit var btnSubmitHome: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_rent_service)

        // Check user login
        val user = auth.currentUser
        if (user == null) {
            Toast.makeText(this, "Please login first", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        etHomeTitle = findViewById(R.id.etHomeTitle)
        etAddress = findViewById(R.id.etAddress)
        etRentPrice = findViewById(R.id.etRentPrice)
        etBedrooms = findViewById(R.id.etBedrooms)
        etContact = findViewById(R.id.etContact)
        btnSubmitHome = findViewById(R.id.btnSubmitHome)

        btnSubmitHome.setOnClickListener {
            submitHomeRentService()
        }
    }

    private fun submitHomeRentService() {
        val title = etHomeTitle.text.toString().trim()
        val address = etAddress.text.toString().trim()
        val rentPrice = etRentPrice.text.toString().trim()
        val bedrooms = etBedrooms.text.toString().trim()
        val contact = etContact.text.toString().trim()

        if (title.isEmpty() || address.isEmpty() || rentPrice.isEmpty() || bedrooms.isEmpty() || contact.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val user = auth.currentUser ?: run {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = user.uid

        val homeData = mapOf(
            "homeTitle" to title,
            "address" to address,
            "rentPrice" to rentPrice,
            "bedrooms" to bedrooms,
            "contact" to contact,
            "ownerId" to userId
        )

        val servicesRef = database.reference.child("services").child(userId)
        val newServiceRef = servicesRef.push()

        newServiceRef.setValue(homeData)
            .addOnSuccessListener {
                Toast.makeText(this, "Home Rent Service added successfully!", Toast.LENGTH_SHORT).show()
                clearForm()
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                Toast.makeText(this, "Failed to add service: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun clearForm() {
        etHomeTitle.text.clear()
        etAddress.text.clear()
        etRentPrice.text.clear()
        etBedrooms.text.clear()
        etContact.text.clear()
    }
}
