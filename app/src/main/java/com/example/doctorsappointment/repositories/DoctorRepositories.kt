package com.example.doctorsappointment.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.doctorsappointment.models.AppointmentModel
import com.example.doctorsappointment.models.DoctorModel
import com.example.doctorsappointment.models.ScheduleModel
import com.example.doctorsappointment.utils.Constants
import com.example.doctorsappointment.utils.DateTimeUtils
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

object DoctorRepositories {
    suspend fun getAllDoctors(): Pair<List<DoctorModel>?, Exception?> {
        return try {
            val db = Firebase.firestore
            val documents = db.collection(Constants.DOCTOR_TABLE).get().await()
            val doctors = documents.toObjects(DoctorModel::class.java)
            Pair(doctors, null)
        } catch (e: Exception) {
            Pair(null, e)
        }
    }

    suspend fun getScheduleOfDoctor(uid: String): Pair<List<ScheduleModel>?, Exception?> {
        return try {
            val db = Firebase.firestore
            val documents = db.collection(Constants.SCHEDULE_TABLE)
                .whereEqualTo("creator", uid)
                .get().await()
            val schedules = documents.toObjects(ScheduleModel::class.java)
            Pair(schedules, null)
        } catch (e: Exception) {
            Pair(null, e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAllAppointments(): Pair<List<AppointmentModel>?, Exception?> {
        return try {
            val db = Firebase.firestore
            val time = DateTimeUtils.getStartingTimestampOfToday()
            val documents = db.collection(Constants.APPOINTMENT_TABLE)
                .whereGreaterThan("createdAt", time)
                .get().await()
            val appointments = documents.toObjects(AppointmentModel::class.java)
            Pair(appointments, null)
        } catch (e: Exception) {
            Pair(null, e)
        }
    }

    suspend fun postData() {
        val db = Firebase.firestore
        val ref = db.collection(Constants.SCHEDULE_TABLE).document()
        ref.set(ScheduleModel(id = ref.id, creator = "111", time = "11:59 PM", totalSlot = 5, available = true)).await()
    }
}