package uz.adkhamjon.mobileprogrammingproject.data.remote.dto

import uz.adkhamjon.mobileprogrammingproject.data.remote.dto.Hit

data class ImageResponse(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)
