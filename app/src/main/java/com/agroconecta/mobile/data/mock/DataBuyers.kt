package com.agroconecta.mobile.data.mock

import com.agroconecta.mobile.data.model.Buyer

object MockBuyers {

    val buyers = listOf(

        Buyer(
            id = 1,
            name = "COMPRADOR MOCK ALEJO 1B",
            email = "buyer1@agroconecta.com",
            phone = "+57 300 000 0001"
        ),

        Buyer(
            id = 2,
            name = "María 2B",
            email = "buyer2@agroconecta.com",
            phone = "+57 300 000 0002"
        ),

        Buyer(
            id = 3,
            name = "Juan 3B",
            email = "buyer3@agroconecta.com",
            phone = "+57 300 000 0003"
        )
    )
}