package com.example.doctorsappointment.dashBoard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.doctorsappointment.R
import com.example.doctorsappointment.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private var _binding: ActivityDashboardBinding? = null
    private val binding get() = _binding!!

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentDashboardView) as NavHostFragment
        navController = navHostFragment.navController

        binding.ivBack.setOnClickListener {
            navController?.navigateUp()
        }

        binding.ivProfileIcon.setOnClickListener {
            if (navController?.currentDestination?.id == R.id.doctorsListFragment) {
                navController?.navigate(R.id.action_doctorsListFragment_to_profileFragment)
            } else if (navController?.currentDestination?.id == R.id.appointmentListFragment) {
                navController?.navigate(R.id.action_appointmentListFragment_to_profileFragment)
            }
        }
    }

    fun setAppBarTitle(title: String) {
        binding.appBarTitle.text = title
    }

    fun hideBackButton() {
        binding.ivBack.visibility = View.GONE
    }

    fun hideProfileIcon() {
        binding.ivProfileIcon.visibility = View.GONE
    }

    fun showProfileIcon() {
        binding.ivProfileIcon.visibility = View.VISIBLE
    }

    fun showBackButton() {
        binding.ivBack.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        navController = null
    }
}