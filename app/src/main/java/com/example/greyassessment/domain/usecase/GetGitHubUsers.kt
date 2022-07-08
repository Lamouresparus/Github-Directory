package com.example.greyassessment.domain.usecase

import com.example.greyassessment.domain.GithubRepository
import com.example.greyassessment.domain.Result
import com.example.greyassessment.domain.model.UserDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGitHubUsers @Inject constructor(private val repository: GithubRepository) {

    suspend fun execute(user: String): Result<List<UserDomain>> {
        return try {
            val response = repository.getUsers(user)
            Result.Success(response)
        } catch (exception: Exception) {
            Result.Error(exception.message ?: "Error Fetching users")
        }

    }
}