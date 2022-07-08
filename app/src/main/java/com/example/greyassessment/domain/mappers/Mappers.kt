package com.example.greyassessment.domain.mappers

import com.example.greyassessment.data.remote.model.RepoRemote
import com.example.greyassessment.data.remote.model.UserDetailRemote
import com.example.greyassessment.data.remote.model.UserRemote
import com.example.greyassessment.domain.model.RepoDomain
import com.example.greyassessment.domain.model.UserDetailDomain
import com.example.greyassessment.domain.model.UserDomain

fun RepoRemote.mapToDomain(): RepoDomain = RepoDomain(
    name = name,
    owner = owner.mapToDomain(),
    watchers = watchers,
    language = language.orEmpty(),
    topics = topics.orEmpty(),
    description = description.orEmpty(),
    repoUrl = repoUrl,
    pushed_at = pushed_at
)

fun UserDetailRemote.mapToDomain(): UserDetailDomain = UserDetailDomain(
    username = login,
    avatarUrl = avatarUrl.orEmpty(),
    githubUrl = htmlUrl,
    name = name.orEmpty(),
    location = location.orEmpty(),
    followers = followers.safeInt(),
    following = following.safeInt(),
    repos = repos.safeInt(),
    bio = bio.orEmpty(),
    email = email.orEmpty(),
    blog = blog.orEmpty()
)

fun UserRemote.mapToDomain(): UserDomain = UserDomain(
    username = login,
    avatarUrl = avatarUrl,
    githubUrl = htmlUrl
)

fun Int?.safeInt(): Int = this ?: 0