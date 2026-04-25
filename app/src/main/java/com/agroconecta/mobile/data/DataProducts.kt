package com.agroconecta.mobile.data

import com.agroconecta.mobile.data.model.Product
import com.agroconecta.mobile.data.model.ProductStatus

object DataProducts {

    private val products = listOf(
        Product(
            id = "1",
            name = "Tomates Cherry Premium",
            category = "Verduras",
            price = 3500.0,
            unit = "kg",
            available = 200,
            minOrder = 5,
            location = "Boyacá",
            rating = 4.8f,
            imageUrl = "",
            farmerId = "farmer_1",
            status = ProductStatus.ACTIVE
        ),
        Product(
            id = "2",
            name = "Lechuga Orgánica",
            category = "Verduras",
            price = 2800.0,
            unit = "kg",
            available = 150,
            minOrder = 3,
            location = "Cundinamarca",
            rating = 4.6f,
            imageUrl = "",
            farmerId = "farmer_2",
            status = ProductStatus.ACTIVE
        ),
        Product(
            id = "3",
            name = "Mango Tommy Exportación",
            category = "Frutas",
            price = 4200.0,
            unit = "kg",
            available = 300,
            minOrder = 10,
            location = "Tolima",
            rating = 4.9f,
            imageUrl = "",
            farmerId = "farmer_3",
            status = ProductStatus.ACTIVE
        ),
        Product(
            id = "4",
            name = "Maíz Amarillo",
            category = "Cereales",
            price = 1800.0,
            unit = "kg",
            available = 500,
            minOrder = 20,
            location = "Córdoba",
            rating = 4.5f,
            imageUrl = "",
            farmerId = "farmer_4",
            status = ProductStatus.ACTIVE
        ),
        Product(
            id = "5",
            name = "Fresas Frescas",
            category = "Frutas",
            price = 6500.0,
            unit = "kg",
            available = 80,
            minOrder = 2,
            location = "Cundinamarca",
            rating = 4.7f,
            imageUrl = "",
            farmerId = "farmer_5",
            status = ProductStatus.ACTIVE
        ),
        Product(
            id = "6",
            name = "Arroz Integral",
            category = "Cereales",
            price = 2200.0,
            unit = "kg",
            available = 1000,
            minOrder = 25,
            location = "Huila",
            rating = 4.4f,
            imageUrl = "",
            farmerId = "farmer_6",
            status = ProductStatus.ACTIVE
        )
    )

    fun getAllProducts(): List<Product> = products

    fun getProductById(productId: String): Product? {
        return products.find { it.id == productId }
    }

    fun getFeaturedProducts(limit: Int = 4): List<Product> {
        return products
            .sortedByDescending { it.rating }
            .take(limit)
    }

    fun getTopProducts(limit: Int = 10): List<Product> {
        return products
            .sortedByDescending { it.rating }
            .take(limit)
    }

    fun getProductsByCategory(category: String): List<Product> {
        return products.filter {
            it.category.equals(category, ignoreCase = true)
        }
    }

    fun searchProducts(query: String): List<Product> {
        if (query.isBlank()) return products

        return products.filter { product ->
            product.name.contains(query, ignoreCase = true) ||
                    product.category.contains(query, ignoreCase = true) ||
                    product.location.contains(query, ignoreCase = true)
        }
    }

    fun getCategories(): List<String> {
        return products
            .map { it.category }
            .distinct()
            .sorted()
    }
}