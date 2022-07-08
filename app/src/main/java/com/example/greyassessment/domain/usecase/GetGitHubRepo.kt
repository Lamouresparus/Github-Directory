package com.example.greyassessment.domain.usecase

import com.example.greyassessment.domain.GithubRepository
import com.example.greyassessment.domain.Result
import com.example.greyassessment.domain.model.RepoDomain
import javax.inject.Inject

class GetGitHubRepo @Inject constructor(private val repository: GithubRepository) {

    suspend fun execute(repo: String): Result<List<RepoDomain>> {
        return try {
            val response = repository.getRepo(repo)
            Result.Success(response)
        } catch (exception: Exception) {
            Result.Error(exception.message ?: "Error Fetching Repo")
        }

    }
}