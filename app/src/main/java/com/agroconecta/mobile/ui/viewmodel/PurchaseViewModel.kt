package com.agroconecta.mobile.ui.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agroconecta.mobile.data.model.Purchase
import com.agroconecta.mobile.data.repository.PurchaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PurchaseViewModel @Inject constructor(
    private val repository: PurchaseRepository
) : ViewModel() {

    var purchases by mutableStateOf<List<Purchase>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun loadPurchasesByBuyer(buyerId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            purchases = repository.getPurchasesByBuyer(buyerId)
            isLoading = false
        }
    }

    fun loadRecentPurchases() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            purchases = repository.getRecentPurchases()
            isLoading = false
        }
    }
}