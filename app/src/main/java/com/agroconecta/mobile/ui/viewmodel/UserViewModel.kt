package com.agroconecta.mobile.ui.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agroconecta.mobile.data.model.Buyer
import com.agroconecta.mobile.data.model.Farmer
import com.agroconecta.mobile.data.repository.UserRepository
import com.agroconecta.mobile.data.session.SessionManager
import com.agroconecta.mobile.data.session.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    var currentFarmer by mutableStateOf<Farmer?>(null)
        private set

    var currentBuyer by mutableStateOf<Buyer?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    // ✅ Estado reactivo de sesión (CLAVE)
    var session by mutableStateOf<UserSession?>(SessionManager.session)
        private set

    val isLoggedIn: Boolean
        get() = session != null

    fun login(email: String, password: String) {
        viewModelScope.launch {
            isLoading = true

            try {
                val result = repository.login(email, password)

                if (result != null) {
                    SessionManager.saveSession(result)
                    session = result
                } else {
                    //ACA SE MANEJA UN ERROR
                }

            } catch (e: Exception) {
                //ACA SE MANEJA UN ERROR
            }

            isLoading = false
        }
    }

    fun logout() {
        SessionManager.clearSession()
        session = null
    }

    fun loadFarmer(id: Int) {
        viewModelScope.launch {
            isLoading = true
            currentFarmer = repository.getFarmerById(id)
            isLoading = false
        }
    }

    fun loadBuyer(id: Int) {
        viewModelScope.launch {
            isLoading = true
            currentBuyer = repository.getBuyerById(id)
            isLoading = false
        }
    }
}