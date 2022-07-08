package com.example.greyassessment.domain.usecase

import com.example.greyassessment.domain.GithubRepository
import com.example.greyassessment.domain.Result
import com.example.greyassessment.domain.model.UserDetailDomain
import javax.inject.Inject

class GetUserDetails @Inject constructor(private val repository: GithubRepository) {
    suspend fun execute(username: String): Result<UserDetailDomain> {
        return try {
            val response = repository.getUserDetail(username)
            Result.Success(response)
        } catch (exception: Exception) {
            Result.Error(exception.message ?: "Error fetching user details")
        }
    }
}