package com.example.doctorsappointment.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.doctorsappointment.R
import com.example.doctorsappointment.dashBoard.DashboardActivity
import com.example.doctorsappointment.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            validateAndLogin()
        }

        binding.btnSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    private fun validateAndLogin() {
//        if (binding.etEmail.text.isNullOrBlank()) {
//            Toast.makeText(requireContext(), "Please enter your email", Toast.LENGTH_SHORT).show()
//            return
//        }
//        if (binding.etPassword.text.isNullOrBlank()) {
//            Toast.makeText(requireContext(), "Please enter your password", Toast.LENGTH_SHORT).show()
//            return
//        }
//        if (!Regex(Constants.emailRegex).matches(binding.etEmail.text!!.trim())) {
//            Toast.makeText(requireContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show()
//            return
//        }
//        if (binding.etPassword.text!!.length < 4) {
//            Toast.makeText(requireContext(), "Password should be atleast length 4", Toast.LENGTH_SHORT).show()
//            return
//        }

        fireBaseLogin()
    }

    private fun fireBaseLogin() {
        val intent = Intent(requireContext(), DashboardActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}