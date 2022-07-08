package com.example.greyassessment.data


import com.example.greyassessment.data.remote.GithubApi
import com.example.greyassessment.domain.GithubRepository
import com.example.greyassessment.domain.mappers.mapToDomain
import com.example.greyassessment.domain.model.RepoDomain
import com.example.greyassessment.domain.model.UserDetailDomain
import com.example.greyassessment.domain.model.UserDomain
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubApi: GithubApi
) :
    GithubRepository {
    override suspend fun getUsers(params: String): List<UserDomain> =
        githubApi.getUsers(params).items.map { it.mapToDomain() }

    override suspend fun getRepo(params: String): List<RepoDomain> =
        githubApi.getRepos(params).items.map { it.mapToDomain() }

    override suspend fun getUserRepos(username: String): List<RepoDomain> =
        githubApi.getUserRepos(username).map { it.mapToDomain() }

    override suspend fun getUserDetail(username: String): UserDetailDomain {
        return githubApi.getUserDetails(username).mapToDomain()
    }

}
