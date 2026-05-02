package com.agroconecta.mobile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agroconecta.mobile.data.model.Product
import com.agroconecta.mobile.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.*
import kotlinx.coroutines.Dispatchers

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

    fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            products = repository.getAllProducts()
            isLoading = false
        }
    }

    fun loadProductById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            selectedProduct = repository.getProductById(id)
            isLoading = false
        }
    }

    fun searchProducts(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            products = repository.searchProducts(query)
            isLoading = false
        }
    }
}