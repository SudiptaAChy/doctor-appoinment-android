package com.example.doctorsappointment.repositories

import com.example.doctorsappointment.models.UserModel
import com.example.doctorsappointment.utils.Constants
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

object AuthRepositories {
    private val auth: FirebaseAuth = Firebase.auth

    fun isLoggedIn(): Boolean {
        val currentUser = auth.currentUser
        return currentUser != null
    }

    suspend fun signUpUser(email: String, password: String): Pair<FirebaseUser?, Exception?> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            Pair(authResult.user, null)
        } catch (e: Exception) {
            Pair(null, e)
        }
    }

    suspend fun saveUserData(user: UserModel): String? {
        val db = Firebase.firestore

        return try {
            db.collection(Constants.USER_TABLE).document(user.uid.toString()).set(user).await()
            null
        } catch (e: Exception) {
            e.toString()
        }
    }

    suspend fun loginUser(email: String, password: String): Pair<FirebaseUser?, Exception?> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            Pair(authResult.user, null)
        } catch (e: Exception) {
            Pair(null, e)
        }
    }

    suspend fun logOutUser() {
        auth.signOut()
    }
}