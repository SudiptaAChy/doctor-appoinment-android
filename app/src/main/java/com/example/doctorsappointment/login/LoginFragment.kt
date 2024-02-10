package com.example.doctorsappointment.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.doctorsappointment.R
import com.example.doctorsappointment.dashBoard.DashboardActivity
import com.example.doctorsappointment.databinding.FragmentLoginBinding
import com.example.doctorsappointment.utils.Constants
import com.example.doctorsappointment.utils.ResponseState

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listenObservers()

        listenOnClickListener()
    }

    private fun listenOnClickListener() {
        binding.btnLogin.setOnClickListener {
            validateAndLogin()
        }

        binding.btnSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    private fun listenObservers() {
        viewModel.userResponse.observe(viewLifecycleOwner) {
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
                    val intent = Intent(requireContext(), DashboardActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }
    }

    private fun showLoader() {
        binding.pbLoading.visibility = View.VISIBLE
        binding.btnLogin.visibility = View.GONE
    }

    private fun hideLoader() {
        binding.pbLoading.visibility = View.GONE
        binding.btnLogin.visibility = View.VISIBLE
    }

    private fun validateAndLogin() {
        if (binding.etEmail.text.isNullOrBlank()) {
            Toast.makeText(requireContext(), "Please enter your email", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.etPassword.text.isNullOrBlank()) {
            Toast.makeText(requireContext(), "Please enter your password", Toast.LENGTH_SHORT).show()
            return
        }
        if (!Regex(Constants.emailRegex).matches(binding.etEmail.text!!.trim())) {
            Toast.makeText(requireContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.etPassword.text!!.length < 6) {
            Toast.makeText(requireContext(), "Password should be atleast length 6", Toast.LENGTH_SHORT).show()
            return
        }

        fireBaseLogin()
    }

    private fun fireBaseLogin() {
        viewModel.loginUser(binding.etEmail.text.toString(), binding.etPassword.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}