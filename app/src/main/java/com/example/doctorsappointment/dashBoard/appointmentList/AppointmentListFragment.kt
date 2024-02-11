package com.example.doctorsappointment.dashBoard.appointmentList

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.example.doctorsappointment.dashBoard.DashboardActivity
import com.example.doctorsappointment.databinding.FragmentAppointmentListBinding
import com.example.doctorsappointment.models.AppointmentItemModel
import com.example.doctorsappointment.utils.ResponseState

@RequiresApi(Build.VERSION_CODES.O)
class AppointmentListFragment : Fragment() {
    private var _binding: FragmentAppointmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AppointmentListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppointmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        listenObservers()
    }

    private fun listenObservers() {
        viewModel.scheduleResponse.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Error -> {
                    hideLoader()
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                }
                ResponseState.Loading -> {
                    showLoader()
                }
                is ResponseState.Success -> {
                    hideLoader()
                    val adapter = AppointmentAdapter(it.data as List<AppointmentItemModel>)
                    binding.rvAppointments.adapter = adapter
                }
            }
        }
    }

    private fun showLoader() {
        binding.pbLoading.visibility = View.VISIBLE
        binding.rvAppointments.visibility = View.GONE
    }

    private fun hideLoader() {
        binding.pbLoading.visibility = View.GONE
        binding.rvAppointments.visibility = View.VISIBLE
    }

    private fun initView() {
        hideLoader()

        (requireActivity() as DashboardActivity).setAppBarTitle("Appointment Schedule")
        (requireActivity() as DashboardActivity).showBackButton()

        viewModel.getScheduleOfDoctor(arguments?.getString("uid") ?: "")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}