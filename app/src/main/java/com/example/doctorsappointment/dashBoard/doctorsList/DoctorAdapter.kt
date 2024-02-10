package com.example.doctorsappointment.dashBoard.doctorsList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorsappointment.R
import com.example.doctorsappointment.models.DoctorModel

class DoctorAdapter(
    private val doctors: List<DoctorModel>,
    private val onItemClick: (position: Int) -> Unit,
): RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {
    class DoctorViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val phone: TextView = itemView.findViewById(R.id.phone)
        val email: TextView = itemView.findViewById(R.id.address)
        val btnSchedule: Button = itemView.findViewById(R.id.btnSchedule)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val viewLayout = LayoutInflater.from(parent.context).inflate(R.layout.doctors_list_item, parent, false)
        return DoctorViewHolder(viewLayout)
    }

    override fun getItemCount(): Int = doctors.size

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.name.text = doctor.name
        holder.phone.text = doctor.phone
        holder.email.text = doctor.email
        holder.btnSchedule.setOnClickListener { onItemClick(position) }
    }
}