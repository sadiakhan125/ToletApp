package com.example.toletapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class home : AppCompatActivity() {

    private lateinit var btnAddRent: Button
    private lateinit var rvHomeList: RecyclerView
    private lateinit var rentList: MutableList<RentService>
    private lateinit var adapter: RentServiceAdapter

    private val database = FirebaseDatabase.getInstance("https://toletapp-35d17-default-rtdb.firebaseio.com")
    private val auth = FirebaseAuth.getInstance()

    private var servicesListener: ValueEventListener? = null
    private val servicesRef = database.reference.child("services")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        btnAddRent = findViewById(R.id.btnAddRent)
        rvHomeList = findViewById(R.id.rvHomeList)

        // Open ProfileActivity on btnAddRent click (since button text is Profile now)
        btnAddRent.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        rentList = mutableListOf()
        adapter = RentServiceAdapter(rentList) { rentService ->
            // Open service detail page on item click
            val intent = Intent(this, ServiceDetailActivity::class.java).apply {
                putExtra("homeTitle", rentService.homeTitle)
                putExtra("address", rentService.address)
                putExtra("rentPrice", rentService.rentPrice)
                putExtra("bedrooms", rentService.bedrooms)
                putExtra("contact", rentService.contact)
            }
            startActivity(intent)
        }

        rvHomeList.layoutManager = LinearLayoutManager(this)
        rvHomeList.adapter = adapter

        loadRentServices()
    }

    private fun loadRentServices() {
        servicesListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                rentList.clear()
                for (userSnapshot in snapshot.children) {
                    Log.d("home", "User ID: ${userSnapshot.key}")
                    for (serviceSnapshot in userSnapshot.children) {
                        val rentService = serviceSnapshot.getValue(RentService::class.java)
                        if (rentService != null) {
                            Log.d("home", "Loaded service: $rentService")
                            rentList.add(rentService)
                        } else {
                            Log.e("home", "Failed to parse service: ${serviceSnapshot.key}")
                        }
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("home", "Failed to load services: ${error.message}")
            }
        }
        servicesRef.addValueEventListener(servicesListener!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        servicesListener?.let {
            servicesRef.removeEventListener(it)
        }
    }
}
