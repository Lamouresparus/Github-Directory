package com.example.greyassessment.ui.users

import app.cash.turbine.test
import com.example.greyassessment.domain.Result
import com.example.greyassessment.domain.model.UserDetailDomain
import com.example.greyassessment.domain.model.UserDomain
import com.example.greyassessment.domain.usecase.GetGitHubUsers
import com.example.greyassessment.domain.usecase.GetUserDetails
import com.example.greyassessment.ui.model.mapToModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class UsersViewModelTest {

    private val getGitHubUsers = mockk<GetGitHubUsers>()
    private val getGitHubUserDetail = mockk<GetUserDetails>()
    private val username = "lamoure"

    private val sut = UsersViewModel(getGitHubUsers, getGitHubUserDetail)

    @Test
    fun `Users Fetched successfully - correct state emitted `() = runTest {

        val userDetail = UserDetailDomain(
            username,
            "image",
            "github.com/lamoure",
            "love",
            "Uyo",
            11,
            12,
            47,
            "",
            "",
            ""
        )
        val users = listOf(UserDomain(username, "image", "github.com/lamoure"))
        coEvery { getGitHubUsers.execute(username) } returns Result.Success(users)
        coEvery { getGitHubUserDetail.execute(username) } returns Result.Success(userDetail)

        sut.uiState.test {
            sut.getGithubUsers(username)

            Assert.assertEquals(UsersViewModel.UiState.Default, awaitItem())


            Assert.assertEquals(UsersViewModel.UiState.Loading, awaitItem())


            val expected = listOf(userDetail.mapToModel())
            val content = awaitItem()

            Assert.assertEquals(UsersViewModel.UiState.Loaded(expected), content)
            Assert.assertEquals(
                username,
                (content as UsersViewModel.UiState.Loaded).users.first().username
            )
            cancelAndConsumeRemainingEvents()
        }

    }

    @Test
    fun `UsersFetched Failed - Correct state emitted`() = runTest {

        val errorMessage = "error fetching github users"
        val result = Result.Error(errorMessage)

        coEvery { getGitHubUsers.execute(username) } returns result

        sut.uiState.test {
            sut.getGithubUsers(username)

            Assert.assertEquals(UsersViewModel.UiState.Default, awaitItem())
            Assert.assertEquals(UsersViewModel.UiState.Loading, awaitItem())

            Assert.assertEquals(UsersViewModel.UiState.Error(errorMessage), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }
}