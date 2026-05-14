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
            available = 200.0,
            minOrder = 5.0,
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
            available = 150.0,
            minOrder = 3.0,
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
            available = 400.0,
            minOrder = 10.0,
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
            available = 350.0,
            minOrder = 8.0,
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
            available = 300.0,
            minOrder = 10.0,
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
            available = 650.0,
            minOrder = 8.0,
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
            available = 280.0,
            minOrder = 6.0,
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
            available = 850.0,
            minOrder = 15.0,
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
            available = 600.0,
            minOrder = 20.0,
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
            available = 450.0,
            minOrder = 15.0,
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
            available = 950.0,
            minOrder = 20.0,
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
            available = 250.0,
            minOrder = 5.0,
            location = "Quindío",
            rating = 4.9f,
            description = "Aguacate Hass premium.",
            farmerId = 3,
            status = ProductStatus.ACTIVE
        )
    )
}