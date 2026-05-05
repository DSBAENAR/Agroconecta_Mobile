package com.agroconecta.mobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agroconecta.mobile.data.model.Buyer
import com.agroconecta.mobile.data.model.Farmer
import com.agroconecta.mobile.data.repository.UserRepository
import com.agroconecta.mobile.data.session.SessionManager
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

    val isLoggedIn: Boolean
        get() = SessionManager.isLoggedIn()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val session = repository.login(email, password)

            if (session != null) {
                SessionManager.saveSession(session)
            }
        }
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