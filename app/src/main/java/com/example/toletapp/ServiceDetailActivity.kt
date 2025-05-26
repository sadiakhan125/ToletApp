package com.example.toletapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class ServiceDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_detail)

        val tvHomeTitle = findViewById<TextView>(R.id.tvDetailHomeTitle)
        val tvAddress = findViewById<TextView>(R.id.tvDetailAddress)
        val tvRentPrice = findViewById<TextView>(R.id.tvDetailRentPrice)
        val tvBedrooms = findViewById<TextView>(R.id.tvDetailBedrooms)
        val tvContact = findViewById<TextView>(R.id.tvDetailContact)

        tvHomeTitle.text = intent.getStringExtra("homeTitle") ?: "N/A"
        tvAddress.text = "Address: " + (intent.getStringExtra("address") ?: "N/A")
        tvRentPrice.text = "Rent Price: " + (intent.getStringExtra("rentPrice") ?: "N/A") + " BDT"
        tvBedrooms.text = "Bedrooms: " + (intent.getStringExtra("bedrooms") ?: "N/A")
        tvContact.text = "Contact: " + (intent.getStringExtra("contact") ?: "N/A")
    }
}
