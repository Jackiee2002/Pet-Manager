package com.kroger.classapp.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


    @JsonClass(generateAdapter = true)
    data class PetCharacter(
        @Json(name = "breeds")
        val breeds: List<Breed>,
        @Json(name = "height")
        val height: Int,
        @Json(name = "id")
        val id: String,
        @Json(name = "url")
        val url: String,
        @Json(name = "width")
        val width: Int?
    ) {
        @JsonClass(generateAdapter = true)
        data class Breed(
            @Json(name = "bred_for")
            val bredFor: String?,
            @Json(name = "breed_group")
            val breedGroup: String? = null,
            @Json(name = "height")
            val height: Height,
            @Json(name = "id")
            val id: Int,
            @Json(name = "life_span")
            val lifeSpan: String,
            @Json(name = "name")
            val name: String,
            @Json(name = "reference_image_id")
            val referenceImageId: String,
            @Json(name = "temperament")
            val temperament: String,
            @Json(name = "weight")
            val weight: Weight
        ) {
            @JsonClass(generateAdapter = true)
            data class Height(
                @Json(name = "imperial")
                val imperial: String,
                @Json(name = "metric")
                val metric: String
            )

            @JsonClass(generateAdapter = true)
            data class Weight(
                @Json(name = "imperial")
                val imperial: String,
                @Json(name = "metric")
                val metric: String
            )
        }
    }