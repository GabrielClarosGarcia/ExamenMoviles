package org.ucb.appp1.di

import org.koin.dsl.module
import org.ucb.appp1.earthquakes.data.datasource.EarthquakeRemoteDataSource
import org.ucb.appp1.earthquakes.data.repository.EarthquakeRepositoryImpl
import org.ucb.appp1.earthquakes.data.service.EarthquakeApiService
import org.ucb.appp1.earthquakes.domain.repository.EarthquakeRepository

val dataModule = module {
    single<EarthquakeRemoteDataSource> { EarthquakeApiService() }
    single<EarthquakeRepository> { EarthquakeRepositoryImpl(get()) }
}
