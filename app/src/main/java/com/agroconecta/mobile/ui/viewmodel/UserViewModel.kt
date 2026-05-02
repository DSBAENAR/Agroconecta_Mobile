package com.agroconecta.mobile.ui.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agroconecta.mobile.data.model.Buyer
import com.agroconecta.mobile.data.model.Farmer
import com.agroconecta.mobile.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    var isLoggedIn by mutableStateOf(false)
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.login(email, password)
            isLoggedIn = result != null
        }
    }

    fun loadFarmer(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            currentFarmer = repository.getFarmerById(id)
        }
    }

    fun loadBuyer(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            currentBuyer = repository.getBuyerById(id)
        }
    }
}