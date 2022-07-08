package com.example.greyassessment.data.remote

import com.example.greyassessment.data.constants.Constants.REPOS
import com.example.greyassessment.data.constants.Constants.REPOSITORIES
import com.example.greyassessment.data.constants.Constants.SEARCH
import com.example.greyassessment.data.constants.Constants.USERS
import com.example.greyassessment.data.remote.model.RepoRemote
import com.example.greyassessment.data.remote.model.RepoResponseModel
import com.example.greyassessment.data.remote.model.UserDetailRemote
import com.example.greyassessment.data.remote.model.UsersResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("$SEARCH/$USERS")
    suspend fun getUsers(
        @Query("q") user: String
    ): UsersResponseModel


    @GET("$SEARCH/$REPOSITORIES")
    suspend fun getRepos(
        @Query("q") repo: String
    ): RepoResponseModel

    @GET("/$USERS/{user_name}")
    suspend fun getUserDetails(@Path("user_name") userName: String): UserDetailRemote


    @GET("/$USERS/{user_name}/$REPOS")
    suspend fun getUserRepos(@Path("user_name") userName: String): List<RepoRemote>
}