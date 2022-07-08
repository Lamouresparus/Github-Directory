package com.example.greyassessment.domain.model


data class RepoDomain (
    val name: String,
    val owner: UserDomain,
    val watchers: Int,
    val language: String,
    val topics: List<String>,
    val description: String,
    val repoUrl: String,
    val pushed_at: String
)