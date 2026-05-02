package com.agroconecta.mobile.data.mock

import com.agroconecta.mobile.data.model.Product
import com.agroconecta.mobile.data.model.ProductStatus

object MockProducts {

    val products = listOf(
        Product(
            id = 1,
            name = "Tomates Cherry Premium",
            category = "Verduras",
            price = 3500.0,
            unit = "kg",
            available = 200f,
            minOrder = 5f,
            location = "Boyacá",
            rating = 4.8f,
            description = "Tomates frescos de alta calidad.",
            farmerId = 1,
            status = ProductStatus.ACTIVE
        ),
        Product(
            id = 2,
            name = "Lechuga Orgánica",
            category = "Verduras",
            price = 2800.0,
            unit = "kg",
            available = 150f,
            minOrder = 3f,
            location = "Cundinamarca",
            rating = 4.6f,
            description = "Lechuga libre de pesticidas.",
            farmerId = 1,
            status = ProductStatus.ACTIVE
        ),
        Product(
            id = 3,
            name = "Papa Criolla",
            category = "Tubérculos",
            price = 2500.0,
            unit = "kg",
            available = 400f,
            minOrder = 10f,
            location = "Boyacá",
            rating = 4.7f,
            description = "Papa criolla seleccionada.",
            farmerId = 1,
            status = ProductStatus.ACTIVE
        ),
        Product(
            id = 4,
            name = "Cebolla Cabezona",
            category = "Verduras",
            price = 2100.0,
            unit = "kg",
            available = 350f,
            minOrder = 8f,
            location = "Boyacá",
            rating = 4.5f,
            description = "Cebolla fresca y uniforme.",
            farmerId = 1,
            status = ProductStatus.ACTIVE
        ),
        Product(
            id = 5,
            name = "Mango Tommy Exportación",
            category = "Frutas",
            price = 4200.0,
            unit = "kg",
            available = 300f,
            minOrder = 10f,
            location = "Tolima",
            rating = 4.9f,
            description = "Mango premium para exportación.",
            farmerId = 2,
            status = ProductStatus.ACTIVE
        ),
        Product(
            id = 6,
            name = "Limón Tahití",
            category = "Frutas",
            price = 3400.0,
            unit = "kg",
            available = 650f,
            minOrder = 8f,
            location = "Tolima",
            rating = 4.8f,
            description = "Limón fresco de exportación.",
            farmerId = 2,
            status = ProductStatus.ACTIVE
        ),
        Product(
            id = 7,
            name = "Maracuyá",
            category = "Frutas",
            price = 4600.0,
            unit = "kg",
            available = 280f,
            minOrder = 6f,
            location = "Tolima",
            rating = 4.7f,
            description = "Maracuyá de alta calidad.",
            farmerId = 2,
            status = ProductStatus.ACTIVE
        ),
        Product(
            id = 8,
            name = "Naranja Valencia",
            category = "Frutas",
            price = 2600.0,
            unit = "kg",
            available = 850f,
            minOrder = 15f,
            location = "Tolima",
            rating = 4.6f,
            description = "Naranja dulce y jugosa.",
            farmerId = 2,
            status = ProductStatus.ACTIVE
        ),
        Product(
            id = 9,
            name = "Café Pergamino",
            category = "Café",
            price = 12500.0,
            unit = "kg",
            available = 600f,
            minOrder = 20f,
            location = "Quindío",
            rating = 5.0f,
            description = "Café especial de altura.",
            farmerId = 3,
            status = ProductStatus.ACTIVE
        ),
        Product(
            id = 10,
            name = "Cacao Premium",
            category = "Cacao",
            price = 9800.0,
            unit = "kg",
            available = 450f,
            minOrder = 15f,
            location = "Quindío",
            rating = 4.8f,
            description = "Cacao fino de aroma.",
            farmerId = 3,
            status = ProductStatus.ACTIVE
        ),
        Product(
            id = 11,
            name = "Plátano Hartón",
            category = "Frutas",
            price = 2100.0,
            unit = "kg",
            available = 950f,
            minOrder = 20f,
            location = "Quindío",
            rating = 4.5f,
            description = "Plátano de excelente calidad.",
            farmerId = 3,
            status = ProductStatus.ACTIVE
        ),
        Product(
            id = 12,
            name = "Aguacate Hass",
            category = "Frutas",
            price = 7800.0,
            unit = "kg",
            available = 250f,
            minOrder = 5f,
            location = "Quindío",
            rating = 4.9f,
            description = "Aguacate Hass premium.",
            farmerId = 3,
            status = ProductStatus.ACTIVE
        )
    )
}