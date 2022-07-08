package com.example.greyassessment.domain

import com.example.greyassessment.domain.model.RepoDomain
import com.example.greyassessment.domain.model.UserDetailDomain
import com.example.greyassessment.domain.model.UserDomain

interface GithubRepository {
    suspend fun getUsers(params: String): List<UserDomain>
    suspend fun getRepo(params: String): List<RepoDomain>
    suspend fun getUserRepos(username: String): List<RepoDomain>
    suspend fun getUserDetail(username: String): UserDetailDomain
}