package com.example.doctorsappointment.dashBoard.appointmentList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorsappointment.R

class AppointmentAdapter(
    private val appointments: List<AppointmentItemModel>
): RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {
    class AppointmentViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val time: TextView = itemView.findViewById(R.id.tvTime)
        val totalSlot: TextView = itemView.findViewById(R.id.tvTotalSlot)
        val availableSlot: TextView = itemView.findViewById(R.id.tvAvailableSlot)
        val slotStatus: TextView = itemView.findViewById(R.id.tvSlotStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val viewLayout = LayoutInflater.from(parent.context).inflate(R.layout.appointment_list_item, parent, false)
        return AppointmentViewHolder(viewLayout)
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.time.text = appointment.time
        holder.totalSlot.text = appointment.totalSlot.toString()
        holder.availableSlot.text = appointment.availableSlot.toString()
        holder.slotStatus.text = appointment.slotStatus
    }
}