package com.example.toletapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RentServiceAdapter(
    private val rentList: List<RentService>,
    private val onItemClick: (RentService) -> Unit
) : RecyclerView.Adapter<RentServiceAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvHomeTitle: TextView = itemView.findViewById(R.id.tvHomeTitle)
        private val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        private val tvRentPrice: TextView = itemView.findViewById(R.id.tvRentPrice)
        private val tvBedrooms: TextView = itemView.findViewById(R.id.tvBedrooms)
        private val tvContact: TextView = itemView.findViewById(R.id.tvContact)

        fun bind(rentService: RentService) {
            tvHomeTitle.text = rentService.homeTitle ?: "N/A"
            tvAddress.text = rentService.address ?: "N/A"
            tvRentPrice.text = "Price: ${rentService.rentPrice ?: "N/A"} BDT"
            tvBedrooms.text = "Bedrooms: ${rentService.bedrooms ?: "N/A"}"
            tvContact.text = "Contact: ${rentService.contact ?: "N/A"}"

            itemView.setOnClickListener {
                onItemClick(rentService)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rent_service, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(rentList[position])
    }

    override fun getItemCount(): Int = rentList.size
}
