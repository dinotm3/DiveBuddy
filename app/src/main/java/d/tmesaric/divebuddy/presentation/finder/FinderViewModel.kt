package d.tmesaric.divebuddy.presentation.finder

import android.content.Context
import android.location.Location
import android.location.LocationRequest
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
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

    // Add a new filteredUsers state
    private val _filteredUsers = mutableStateOf<List<User>?>(null)
    val filteredUsers: State<List<User>?> = _filteredUsers

    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            try {
                _state.value = state.value.copy(isLoading = true)
                val users = api.getUsers()
                _state.value = state.value.copy(
                    users = users,
                    isLoading = false
                )

                // Initialize filteredUsers with all users
                _filteredUsers.value = users
            } catch (e: Exception) {
                Log.e("FinderViewModel", "getUsers", e)
                _state.value = state.value.copy(isLoading = false)
            }
        }
    }

    fun filterUsersInRange(chosenRange: Float, location: Location, context: Context) {
        val usersInRange = findUsersInRange(state.value.users, location, context, chosenRange)
        _filteredUsers.value = usersInRange
    }

    data class UsersState(
        val users: List<User>? = null,
        val isLoading: Boolean = false
    )
}