package com.example.doctorsappointment.dashBoard.appointmentList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorsappointment.R
import com.example.doctorsappointment.models.AppointmentItemModel
import com.example.doctorsappointment.utils.SlotStatus

class AppointmentAdapter(
    private val context: Context,
    private val appointments: MutableList<AppointmentItemModel>,
    private val onItemClick: (Int) -> Unit,
): RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {
    fun changedItem(position: Int, newData: AppointmentItemModel) {
        appointments[position] = newData
        notifyItemChanged(position)
    }

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
        holder.totalSlot.text = "Total Slot: ${appointment.totalSlot}"
        holder.availableSlot.text = "Available Slot: ${appointment.availableSlot}"
        holder.slotStatus.text = appointment.slotStatus

        holder.slotStatus.apply {
            background = if (appointment.slotStatus?.uppercase() == SlotStatus.AVAILABLE.title.uppercase()) {
                ContextCompat.getDrawable(context, R.drawable.circular_green_background)
            } else if(appointment.slotStatus?.uppercase() == SlotStatus.BOOKED.title.uppercase()) {
                ContextCompat.getDrawable(context, R.drawable.circular_purple_background)
            } else if(appointment.slotStatus?.uppercase() == SlotStatus.FILLED_UP.title.uppercase()) {
                ContextCompat.getDrawable(context, R.drawable.circular_red_background)
            } else if(appointment.slotStatus?.uppercase() == SlotStatus.FINISHED.title.uppercase()) {
                ContextCompat.getDrawable(context, R.drawable.circular_light_red_background)
            } else {
                ContextCompat.getDrawable(context, R.drawable.circular_grey_background)
            }
        }

        if (appointment.slotStatus?.uppercase() == SlotStatus.NOT_AVAILABLE.title.uppercase()) {
            holder.apply {
                time.setTextColor(ContextCompat.getColor(context, R.color.disable_text_color))
                totalSlot.setTextColor(ContextCompat.getColor(context, R.color.disable_text_color))
                availableSlot.setTextColor(ContextCompat.getColor(context, R.color.disable_text_color))
            }
        }

        holder.slotStatus.setOnClickListener {
            if (appointment.slotStatus?.equals(SlotStatus.AVAILABLE.title, true) == true) {
                onItemClick(position)
            }
        }
    }
}