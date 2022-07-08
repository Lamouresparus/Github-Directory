package com.example.greyassessment.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.greyassessment.domain.Result
import com.example.greyassessment.domain.usecase.GetGitHubUsers
import com.example.greyassessment.domain.usecase.GetUserDetails
import com.example.greyassessment.ui.model.UserDetail
import com.example.greyassessment.ui.model.mapToRModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getGitHubUsers: GetGitHubUsers,
    private val getUserDetails: GetUserDetails
) : ViewModel() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    private val _uiState = MutableStateFlow<UiState>(UiState.Default)
    val uiState: LiveData<UiState> = _uiState.asLiveData()

    fun getGithubUsers(username: String) {
        viewModelScope.launch(dispatcher) {
            _uiState.emit(UiState.Loading)
            when (val result = getGitHubUsers.execute(username)) {
                is Result.Success -> {
                    val users = mutableListOf<UserDetail>()
                    result.data.map {
                        when (val user = getUserDetails.execute(it.username)) {
                            is Result.Error -> _uiState.emit(UiState.Error(user.errorMsg))
                            is Result.Success -> users.add(user.data.mapToRModel())
                        }
                    }
                    _uiState.emit(UiState.Loaded(users))

                }
                is Result.Error -> _uiState.emit(UiState.Error(result.errorMsg))
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        object Default : UiState()
        data class Error(val message: String) : UiState()
        data class Loaded(val users: List<UserDetail>) : UiState()
    }
}