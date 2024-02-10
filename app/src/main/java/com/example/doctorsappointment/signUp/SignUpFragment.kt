package com.example.doctorsappointment.signUp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.doctorsappointment.databinding.FragmentSignUpBinding
import com.example.doctorsappointment.models.UserModel
import com.example.doctorsappointment.repositories.AuthRepositories
import com.example.doctorsappointment.utils.Constants
import com.example.doctorsappointment.utils.ResponseState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.auth.User

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listenObservers()

        listenOnClickListener()
    }

    private fun listenOnClickListener() {
        binding.btnLogin.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSignup.setOnClickListener { validateSignUp() }
    }

    private fun listenObservers() {
        viewModel.userResponse.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Loading -> {
                    showLoader()
                }
                is ResponseState.Error -> {
                    hideLoader()
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
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
        binding.btnSignup.visibility = View.GONE
    }

    private fun hideLoader() {
        binding.pbLoading.visibility = View.GONE
        binding.btnSignup.visibility = View.VISIBLE
    }

    private fun validateSignUp() {
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

        fireBaseSignUp()
    }

    private fun fireBaseSignUp() {
        viewModel.signUpUser(
            email = binding.etEmail.text.toString(),
            password = binding.etPassword.text.toString(),
            name = binding.etName.text?.trim().toString(),
            phone = binding.etPhone.text?.trim().toString(),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}