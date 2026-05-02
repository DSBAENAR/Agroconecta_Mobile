package com.agroconecta.mobile.di

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

    @Provides
    @Singleton
    fun provideProductRepository(): ProductRepository =
        ProductRepositoryImpl()

    @Provides
    @Singleton
    fun providePurchaseRepository(): PurchaseRepository =
        PurchaseRepositoryImpl()

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository =
        UserRepositoryImpl()
}