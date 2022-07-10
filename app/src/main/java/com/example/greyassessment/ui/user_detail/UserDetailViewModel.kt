package com.example.greyassessment.ui.user_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greyassessment.domain.Result
import com.example.greyassessment.domain.usecase.GetGitHubUserRepo
import com.example.greyassessment.ui.model.Repo
import com.example.greyassessment.ui.model.mapToModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getGitHubUserRepo: GetGitHubUserRepo
) : ViewModel() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState as StateFlow<UiState>

    fun getGithubUserRepo(username: String) {
        viewModelScope.launch(dispatcher) {
            _uiState.emit(UiState.Loading)
            when (val result = getGitHubUserRepo.execute(username)) {
                is Result.Success -> _uiState.emit(UiState.Loaded(result.data.map { it.mapToModel() }))
                is Result.Error -> _uiState.emit(UiState.Error(result.errorMsg))
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Error(val message: String) : UiState()
        data class Loaded(val repos: List<Repo>) : UiState()
    }
}