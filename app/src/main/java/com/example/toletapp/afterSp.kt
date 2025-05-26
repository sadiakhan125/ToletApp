package com.example.toletapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class afterSp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_sp) // replace with your XML file name

        val btnHomeowner = findViewById<Button>(R.id.btnHomeowner)
        val btnRental = findViewById<Button>(R.id.btnRental)

        btnHomeowner.setOnClickListener {
            val intent = Intent(this, loginpage::class.java)
            startActivity(intent)
        }

        btnRental.setOnClickListener {
            val intent = Intent(this, home::class.java)
            startActivity(intent)
        }
    }
}
