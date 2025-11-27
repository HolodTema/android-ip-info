package com.terabyte.domain.model

import java.util.UUID

data class IPInfo(
    val id: UUID,
    val ip: String,
    val hostname: String,
    val country: String,
    val region: String,
    val city: String,
    val organization: String,
    val timezone: String,
    val latitude: Double?,
    val longitude: Double?
)
