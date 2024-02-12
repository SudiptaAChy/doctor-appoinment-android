package com.example.doctorsappointment.dashBoard.profile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.example.doctorsappointment.MainActivity
import com.example.doctorsappointment.dashBoard.DashboardActivity
import com.example.doctorsappointment.databinding.FragmentProfileBinding
import com.example.doctorsappointment.models.BookedAppointmentItemModel
import com.example.doctorsappointment.utils.ResponseState

@RequiresApi(Build.VERSION_CODES.O)
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        listenClickListener()

        listenObserver()
    }

    private fun listenObserver() {
        viewModel.appointmentsResponse.observe(viewLifecycleOwner) {
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
                    val adapter = BookedAppointmentAdapter(it.data as List<BookedAppointmentItemModel>)
                    binding.rvBookedAppnt.adapter = adapter
                }
            }
        }
    }

    private fun listenClickListener() {
        binding.btnLogout.setOnClickListener {
            viewModel.logoutUser()
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun initView() {
        hideLoader()

        (requireActivity() as DashboardActivity).setAppBarTitle("My Profile")
        (requireActivity() as DashboardActivity).showBackButton()
        (requireActivity() as DashboardActivity).hideProfileIcon()

        viewModel.getAllBookedAppointments()
    }

    private fun showLoader() {
        binding.pbLoading.visibility = View.VISIBLE
        binding.rvBookedAppnt.visibility = View.GONE
    }

    private fun hideLoader() {
        binding.pbLoading.visibility = View.GONE
        binding.rvBookedAppnt.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}