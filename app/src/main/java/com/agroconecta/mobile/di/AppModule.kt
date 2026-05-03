package com.agroconecta.mobile.di

import com.agroconecta.mobile.data.remote.RetrofitInstance
import com.agroconecta.mobile.data.remote.api.ProductApiService
import com.agroconecta.mobile.data.remote.api.PurchaseApiService
import com.agroconecta.mobile.data.remote.api.UserApiService
import com.agroconecta.mobile.data.repository.ProductRepository
import com.agroconecta.mobile.data.repository.ProductRepositoryImpl
import com.agroconecta.mobile.data.repository.PurchaseRepository
import com.agroconecta.mobile.data.repository.PurchaseRepositoryImpl
import com.agroconecta.mobile.data.repository.UserRepository
import com.agroconecta.mobile.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // ✅ Proveemos las APIs de Retrofit como dependencias inyectables
    @Provides
    @Singleton
    fun provideProductApiService(): ProductApiService =
        RetrofitInstance.productApi

    @Provides
    @Singleton
    fun providePurchaseApiService(): PurchaseApiService =
        RetrofitInstance.purchaseApi

    @Provides
    @Singleton
    fun provideUserApiService(): UserApiService =
        RetrofitInstance.userApi


    @Provides
    @Singleton
    fun provideProductRepository(
        api: ProductApiService
    ): ProductRepository = ProductRepositoryImpl(api)

    @Provides
    @Singleton
    fun providePurchaseRepository(
        api: PurchaseApiService
    ): PurchaseRepository = PurchaseRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideUserRepository(
        api: UserApiService
    ): UserRepository = UserRepositoryImpl(api)
}