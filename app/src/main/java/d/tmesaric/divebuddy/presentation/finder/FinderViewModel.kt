package d.tmesaric.divebuddy.presentation.finder

import android.app.Application
import android.content.Context
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import d.tmesaric.divebuddy.common.LocationHelper
import d.tmesaric.divebuddy.data.UsersApi
import d.tmesaric.divebuddy.domain.location.LocationClientImpl
import d.tmesaric.divebuddy.domain.model.User
import d.tmesaric.divebuddy.domain.model.getLastKnownLocation
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinderViewModel @Inject constructor(
    private val api: UsersApi,
    context: Context
) : ViewModel() {

    private val _state = mutableStateOf(UsersState())
    val state: State<UsersState> = _state

    private val _filteredUsers = mutableStateOf<List<User>?>(null)
    val filteredUsers: State<List<User>?> = _filteredUsers

    // TODO: replace with getLoggedInUser
    val user = User()

    init {
        getUsers()
        getAndUpdateUserLocation(user, context)
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
                _filteredUsers.value = users
            } catch (e: Exception) {
                Log.e("FinderViewModel", "getUsers", e)
                _state.value = state.value.copy(isLoading = true)
            }
        }
    }

    private fun getAndUpdateUserLocation(user: User, context: Context) {
        val fusedLocationProviderClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
        val locationClient = LocationClientImpl(context, fusedLocationProviderClient)
        viewModelScope.launch {
            try {
                locationClient.getLocation()
                api.updateUserPosition(
                    user.id,
                    user.lastKnownPosition.latitude,
                    user.lastKnownPosition.longitude
                )
            } catch (e: Exception) {
                Log.e("FinderViewModel", "getAndUpdateUserLocation", e)
            }
        }
    }

    fun filterUsersInRange(chosenRange: Float, location: Location, context: Context) {
        val usersInRange = findUsersInRange(state.value.users, location, chosenRange)
        _filteredUsers.value = usersInRange
    }

    private fun findUsersInRange(
        allUsers: List<User>?,
        location: Location,
        chosenRange: Float
    ): List<User>? {
        var distance: Float
        val usersInRange: MutableList<User>? = mutableListOf()
        if (allUsers != null) {
            for (user: User in allUsers) {
                distance = location.distanceTo(getLastKnownLocation(user.lat, user.lng))
                if (toKm(distance) <= chosenRange) {
                    usersInRange!!.add(user)
                }
            }
        }
        return usersInRange;
    }

    private fun toKm(chosenRange: Float): Float {
        return chosenRange / 1000
    }

    data class UsersState(
        val users: List<User>? = null,
        val isLoading: Boolean = false
    )
}