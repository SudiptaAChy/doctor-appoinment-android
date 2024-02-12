package com.example.doctorsappointment.dashBoard.doctorsList

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.doctorsappointment.R
import com.example.doctorsappointment.dashBoard.DashboardActivity
import com.example.doctorsappointment.databinding.FragmentDoctorsListBinding
import com.example.doctorsappointment.models.DoctorModel
import com.example.doctorsappointment.utils.ResponseState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class DoctorsListFragment : Fragment() {
    private var _binding: FragmentDoctorsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DoctorListViewModel by viewModels()

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
        loadDoctorsData()
        listenObservers()
    }

    private fun listenObservers() {
        viewModel.doctorResponse.observe(viewLifecycleOwner) {
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
                    val doctors = (it.data as? List<DoctorModel>) ?: listOf()
                    val adapter = DoctorAdapter(doctors) { position ->
                        val bundle = bundleOf("uid" to doctors[position].uid)
                        findNavController().navigate(R.id.action_doctorsListFragment_to_appointmentListFragment, bundle)
                    }
                    binding.rvDoctors.adapter = adapter
                }
            }
        }
    }

    private fun showLoader() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        binding.pbLoading.visibility = View.GONE
    }

    private fun loadDoctorsData() {
        viewModel.getAllDoctorList()
    }

    private fun initView() {
        hideLoader()

        (requireActivity() as DashboardActivity).setAppBarTitle("Doctor's List")
        (requireActivity() as DashboardActivity).hideBackButton()
        (requireActivity() as DashboardActivity).showProfileIcon()

        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy hh:mm a", Locale.ENGLISH)
        binding.tvDateTime.text = currentDateTime.format(formatter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}