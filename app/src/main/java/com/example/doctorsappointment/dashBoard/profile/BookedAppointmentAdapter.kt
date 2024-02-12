package com.example.doctorsappointment.dashBoard.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorsappointment.R
import com.example.doctorsappointment.models.BookedAppointmentItemModel

class BookedAppointmentAdapter(
    private val appointments: List<BookedAppointmentItemModel>,
): RecyclerView.Adapter<BookedAppointmentAdapter.AppointmentViewHolder>() {
    class AppointmentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val drName: TextView = itemView.findViewById(R.id.tvDoctorName)
        val dateTime: TextView = itemView.findViewById(R.id.tvAppntDateTime)
        val timeSlot: TextView = itemView.findViewById(R.id.tvTimeSlot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val viewLayout = LayoutInflater.from(parent.context).inflate(R.layout.booked_appointment_item, parent, false)
        return AppointmentViewHolder(viewLayout)
    }

    override fun getItemCount(): Int = appointments.size

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.drName.text = appointment.drName
        holder.timeSlot.text = "Slot: ${appointment.timeSlot}"
        holder.dateTime.text = "Booking date: ${appointment.date}"
    }
}