package com.agroconecta.mobile.ui.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agroconecta.mobile.data.model.Product
import com.agroconecta.mobile.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    var products by mutableStateOf<List<Product>>(emptyList())
        private set

    var selectedProduct by mutableStateOf<Product?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    init {
        loadProducts()
    }

    // ✅ Sin Dispatchers.IO — el repositorio ya usa withContext internamente
    // El estado de Compose SOLO puede modificarse en el hilo principal
    fun loadProducts() {
        viewModelScope.launch {
            isLoading = true
            products = repository.getAllProducts()
            isLoading = false
        }
    }

    fun loadProductById(id: Int) {
        viewModelScope.launch {
            isLoading = true
            selectedProduct = repository.getProductById(id)
            isLoading = false
        }
    }

    fun searchProducts(query: String) {
        viewModelScope.launch {
            isLoading = true
            products = repository.searchProducts(query)
            isLoading = false
        }
    }
}