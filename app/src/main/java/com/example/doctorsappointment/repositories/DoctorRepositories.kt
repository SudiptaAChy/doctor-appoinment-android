package com.example.doctorsappointment.repositories

import com.example.doctorsappointment.models.DoctorModel
import com.example.doctorsappointment.utils.Constants
import com.google.firebase.Firebase
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

object DoctorRepositories {
    suspend fun getAllDoctors(): Pair<List<DoctorModel>?, Exception?> {
        return try {
            val db = Firebase.firestore
            val documents = db.collection(Constants.DOCTOR_TABLE).get(Source.CACHE).await()
            val doctors = documents.toObjects(DoctorModel::class.java)
            Pair(doctors, null)
        } catch (e: Exception) {
            Pair(null, e)
        }
    }
}