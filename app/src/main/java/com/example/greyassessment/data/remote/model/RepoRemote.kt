package com.example.greyassessment.data.remote.model

import com.google.gson.annotations.SerializedName

data class RepoRemote(
    val name: String,
    val owner: UserRemote,
    val watchers: Int,
    val language: String?,
    val topics: List<String>?,
    val description: String?,
    @SerializedName("html_url")
    val repoUrl: String,
    val pushed_at: String
)

data class RepoResponseModel(
    @SerializedName("total_count") var totalCount: Long,
    @SerializedName("incomplete_results") var incompleteResults: Boolean,
    @SerializedName("items") var items: List<RepoRemote>
)