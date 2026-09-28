package org.ucb.appp1.earthquakes.data.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.ucb.appp1.earthquakes.data.datasource.EarthquakeRemoteDataSource
import org.ucb.appp1.earthquakes.data.dto.EarthquakeResponseDto

class EarthquakeApiService : EarthquakeRemoteDataSource {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    override suspend fun getEarthquakes(): EarthquakeResponseDto {
        val response = client.get("https://earthquake.usgs.gov/fdsnws/event/1/query") {
            parameter("format", "geojson")
            parameter("minmagnitude", 5)
            parameter("limit", 3)
        }
        return response.body()
    }
}
