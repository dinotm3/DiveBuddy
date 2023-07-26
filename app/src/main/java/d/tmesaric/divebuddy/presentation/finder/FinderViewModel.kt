package d.tmesaric.divebuddy.presentation.finder

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import d.tmesaric.divebuddy.data.UsersApi
import d.tmesaric.divebuddy.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinderViewModel @Inject constructor(
    private val api: UsersApi
) : ViewModel() {

    private val _state = mutableStateOf(UsersState())
    val state: State<UsersState> = _state

    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            try {
                _state.value = state.value.copy(isLoading = true)
                _state.value = state.value.copy(
                    users = api.getUsers(),
                    isLoading = false
                )
            } catch (e: java.lang.Exception) {
                Log.e("FinderViewModel", "getUsers", e)
                _state.value = state.value.copy(isLoading = false)
            }
        }
    }
    data class UsersState(
        val users: List<User>? = null,
        val isLoading: Boolean = false
    )
}