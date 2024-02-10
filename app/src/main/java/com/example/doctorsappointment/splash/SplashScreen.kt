package com.example.doctorsappointment.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.example.doctorsappointment.MainActivity
import com.example.doctorsappointment.dashBoard.DashboardActivity
import com.example.doctorsappointment.databinding.ActivitySplashScreenBinding
import com.example.doctorsappointment.repositories.AuthRepositories

class SplashScreen : AppCompatActivity() {
    private var _binding: ActivitySplashScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.isUserLoggedIn()

            viewModel.userLoggedIn.observe(this) {
                if (it==1) {
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, 100)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}