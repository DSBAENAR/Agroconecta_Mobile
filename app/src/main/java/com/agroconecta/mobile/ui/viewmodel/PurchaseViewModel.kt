package com.agroconecta.mobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agroconecta.mobile.data.model.Purchase
import com.agroconecta.mobile.data.repository.PurchaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun loadPurchases() {
        loadRecentPurchases()
    }

    fun loadPurchasesByBuyer(buyerId: Int) {
        viewModelScope.launch { // ✅ Sin Dispatchers.IO
            isLoading = true
            try {
                purchases = repository.getPurchasesByBuyer(buyerId)
            } finally {
                isLoading = false
            }
        }
    }

    fun loadRecentPurchases() {
        viewModelScope.launch { // ✅ Sin Dispatchers.IO
            isLoading = true
            try {
                purchases = repository.getRecentPurchases()
            } finally {
                isLoading = false
            }
        }
    }
}