package com.example.greyassessment.ui.repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.greyassessment.domain.Result
import com.example.greyassessment.domain.usecase.GetGitHubRepo
import com.example.greyassessment.ui.model.Repo
import com.example.greyassessment.ui.model.mapToRModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
    private val getGitHubRepo: GetGitHubRepo
) : ViewModel() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    private val _uiState = MutableStateFlow<UiState>(UiState.Default)
    val uiState = _uiState.asLiveData()

    fun getGithubRepo(repo: String) {
        viewModelScope.launch(dispatcher) {
            _uiState.emit(UiState.Loading)
            when (val result = getGitHubRepo.execute(repo)) {
                is Result.Success -> _uiState.emit(UiState.Loaded(result.data.map { it.mapToRModel() }))
                is Result.Error -> _uiState.emit(UiState.Error(result.errorMsg))
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        object Default : UiState()
        data class Error(val message: String) : UiState()
        data class Loaded(val repos: List<Repo>) : UiState()
    }
}