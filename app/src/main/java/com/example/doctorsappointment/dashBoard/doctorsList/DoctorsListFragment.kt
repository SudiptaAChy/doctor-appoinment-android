package com.example.doctorsappointment.dashBoard.doctorsList

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.doctorsappointment.R
import com.example.doctorsappointment.dashBoard.DashboardActivity
import com.example.doctorsappointment.databinding.FragmentDoctorsListBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class DoctorsListFragment : Fragment() {
    private var _binding: FragmentDoctorsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoctorsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()
    }

    private fun initAdapter() {
        val doctors = listOf(
            DoctorModel(name = "Dasfjksffdggg", phone = "012345678990", address = "Dhaka"),
            DoctorModel(name = "Dasfjksf", phone = "012345678990", address = "Dhaka"),
            DoctorModel(name = "Dasfjksf", phone = "012345678990", address = "Dhaka"),
            DoctorModel(name = "Dasfjksf", phone = "012345678990", address = "Dhaka"),
            DoctorModel(name = "Dasfjksf", phone = "012345678990", address = "Dhaka"),
            DoctorModel(name = "Dasfjksf", phone = "012345678990", address = "Dhaka"),
            DoctorModel(name = "Dasfjksf", phone = "012345678990", address = "Dhaka"),
        )
        val adapter = DoctorAdapter(doctors) { position ->
            findNavController().navigate(R.id.action_doctorsListFragment_to_appointmentListFragment)
        }
        binding.rvDoctors.adapter = adapter
    }

    private fun initView() {
        (requireActivity() as DashboardActivity).setAppBarTitle("Doctor's List")
        (requireActivity() as DashboardActivity).hideBackButton()

        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy hh:mm a", Locale.ENGLISH)
        binding.tvDateTime.text = currentDateTime.format(formatter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}