package com.example.converter.domain.model

data class CurrencyResponse(
    val meta: MetaData,
    val data: Map<String, CurrencyData>
)

data class MetaData(
    val last_updated_at: String
)

data class CurrencyData(
    val code: String,
    val value: Double
)
