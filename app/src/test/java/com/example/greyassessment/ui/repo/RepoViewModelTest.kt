package com.example.greyassessment.ui.repo

import app.cash.turbine.test
import com.example.greyassessment.domain.Result
import com.example.greyassessment.domain.model.RepoDomain
import com.example.greyassessment.domain.model.UserDomain
import com.example.greyassessment.domain.usecase.GetGitHubRepo
import com.example.greyassessment.ui.model.mapToModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class RepoViewModelTest {

    private val getGitHubRepo = mockk<GetGitHubRepo>()
    private val repoName = "GreyAssessment"

    private val sut = RepoViewModel(getGitHubRepo)

    @Test
    fun `Repo Fetched successfully - correct state emitted `() = runTest {
        val user = UserDomain("lamoure", "image", "github.com/lamoure")
        val repo = listOf(RepoDomain(repoName, user, 2, "Kotlin", emptyList(), "", "", ""))

        coEvery { getGitHubRepo.execute(repoName) } returns Result.Success(repo)

        sut.uiState.test {
            sut.getGithubRepo(repoName)

            Assert.assertEquals(RepoViewModel.UiState.Default, awaitItem())


            Assert.assertEquals(RepoViewModel.UiState.Loading, awaitItem())


            val expected = repo.map { it.mapToModel() }
            val content = awaitItem()

            Assert.assertEquals(RepoViewModel.UiState.Loaded(expected), content)
            Assert.assertEquals(
                repoName,
                (content as RepoViewModel.UiState.Loaded).repos.first().name
            )
            cancelAndConsumeRemainingEvents()
        }

    }

    @Test
    fun `RepoFetched Failed - Correct state emitted`() = runTest {

        val errorMessage = "error fetching github Repository"
        val result = Result.Error(errorMessage)

        coEvery { getGitHubRepo.execute(repoName) } returns result

        sut.uiState.test {
            sut.getGithubRepo(repoName)

            Assert.assertEquals(RepoViewModel.UiState.Default, awaitItem())
            Assert.assertEquals(RepoViewModel.UiState.Loading, awaitItem())

            Assert.assertEquals(RepoViewModel.UiState.Error(errorMessage), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }
}