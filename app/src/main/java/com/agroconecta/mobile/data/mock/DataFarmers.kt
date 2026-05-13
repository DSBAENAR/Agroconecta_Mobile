package com.agroconecta.mobile.data.mock

import com.agroconecta.mobile.data.model.Farmer
import com.agroconecta.mobile.data.model.UserStatus

object MockFarmers {

    val farmers = mutableListOf(
        Farmer(
            id = 1,
            name = "Juan Pérez",
            location = "Boyacá",
            email = "juan.perez@agroconecta.com",
            phone = "3001234567",
            imageUrl = "https://i.pravatar.cc/300?img=1",
            status = UserStatus.ACTIVE
        ),
        Farmer(
            id = 2,
            name = "María Gómez",
            location = "Tolima",
            email = "maria.gomez@agroconecta.com",
            phone = "3007654321",
            imageUrl = "https://i.pravatar.cc/300?img=2",
            status = UserStatus.ACTIVE
        ),
        Farmer(
            id = 3,
            name = "Carlos Rodríguez",
            location = "Quindío",
            email = "carlos.rodriguez@agroconecta.com",
            phone = "3019876543",
            imageUrl = "https://i.pravatar.cc/300?img=3",
            status = UserStatus.ACTIVE
        )
    )
}