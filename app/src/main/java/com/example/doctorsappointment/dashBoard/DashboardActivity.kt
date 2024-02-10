package com.example.doctorsappointment.dashBoard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.example.doctorsappointment.R
import com.example.doctorsappointment.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private var _binding: ActivityDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.dashboard_nav_graph) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigateUp()
        }
    }

    fun setAppBarTitle(title: String) {
        binding.appBarTitle.text = title
    }

    fun hideBackButton() {
        binding.ivBack.visibility = View.GONE
    }

    fun showBackButton() {
        binding.ivBack.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}