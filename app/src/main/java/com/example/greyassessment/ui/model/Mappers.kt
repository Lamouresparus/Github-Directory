package com.example.greyassessment.ui.model

import com.example.greyassessment.domain.model.RepoDomain
import com.example.greyassessment.domain.model.UserDetailDomain
import com.example.greyassessment.domain.model.UserDomain

fun RepoDomain.mapToRModel(): Repo = Repo(
    name = name,
    owner = owner.mapToRModel(),
    watchers = watchers,
    language = language,
    topics = topics,
    description = description,
    repoUrl = repoUrl,
    pushed_at = pushed_at
)

fun UserDetailDomain.mapToRModel(): UserDetail = UserDetail(
    username = username,
    avatarUrl = avatarUrl,
    githubUrl = githubUrl,
    name = name,
    location = location,
    followers = followers,
    following = following,
    repos = repos,
    bio = bio,
    email = email,
    blog = blog
)

fun UserDomain.mapToRModel(): User = User(
    username = username,
    avatarUrl = avatarUrl,
    githubUrl = githubUrl
)