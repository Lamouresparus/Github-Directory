package com.example.greyassessment.ui.user_detail

import app.cash.turbine.test
import com.example.greyassessment.domain.Result
import com.example.greyassessment.domain.model.RepoDomain
import com.example.greyassessment.domain.model.UserDomain
import com.example.greyassessment.domain.usecase.GetGitHubUserRepo
import com.example.greyassessment.ui.model.mapToModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class UserDetailViewModelTest {

    private val getGitHubUserRepo = mockk<GetGitHubUserRepo>()
    private val username = "lamoure"

    private val sut = UserDetailViewModel(getGitHubUserRepo)

    @Test
    fun `User Repo Fetched successfully - correct state emitted `() = runTest {

        val user = UserDomain(username, "image", "github.com/lamoure")
        val repo = RepoDomain("Grey Assessment", user, 2, "Kotlin", emptyList(), "", "", "")

        coEvery { getGitHubUserRepo.execute(username) } returns Result.Success(listOf(repo))

        sut.uiState.test {
            sut.getGithubUserRepo(username)



            Assert.assertEquals(UserDetailViewModel.UiState.Loading, awaitItem())


            val expected = listOf(repo.mapToModel())
            val content = awaitItem()

            Assert.assertEquals(UserDetailViewModel.UiState.Loaded(expected), content)
            Assert.assertEquals(
                username,
                (content as UserDetailViewModel.UiState.Loaded).repos.first().owner.username
            )
            cancelAndConsumeRemainingEvents()
        }

    }

    @Test
    fun `UserRepo Failed - Correct state emitted`() = runTest {

        val errorMessage = "error fetching github user repos"
        val result = Result.Error(errorMessage)

        coEvery { getGitHubUserRepo.execute(username) } returns result

        sut.uiState.test {
            sut.getGithubUserRepo(username)

            Assert.assertEquals(UserDetailViewModel.UiState.Loading, awaitItem())

            Assert.assertEquals(UserDetailViewModel.UiState.Error(errorMessage), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }
}