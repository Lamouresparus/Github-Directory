package com.example.greyassessment.domain.usecase

import com.example.greyassessment.domain.GithubRepository
import com.example.greyassessment.domain.Result
import com.example.greyassessment.domain.model.RepoDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGitHubUserRepo @Inject constructor(private val repository: GithubRepository) {

    suspend fun execute(username: String): Result<List<RepoDomain>> {
        return try {
            val response = repository.getUserRepos(username)
            Result.Success(response)
        } catch (exception: Exception) {
            Result.Error(exception.message ?: "Error Fetching Repos for $username")
        }

    }
}