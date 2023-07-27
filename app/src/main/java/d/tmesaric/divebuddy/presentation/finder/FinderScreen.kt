package d.tmesaric.divebuddy.presentation.finder

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import d.tmesaric.divebuddy.domain.model.Country
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import d.tmesaric.divebuddy.domain.model.User
import d.tmesaric.divebuddy.domain.model.getLastKnownLocation

@OptIn(ExperimentalCoilApi::class)
@Composable
fun FinderScreen(
    navController: NavController,
) {
    val maxRange = 1f..1000f;
    var sliderPosition by remember { mutableStateOf(0f) }
    val context = LocalContext.current
    var chosenRange = sliderPosition;

    val viewModel: FinderViewModel = hiltViewModel()
    var state = viewModel.state.value
    val allUsers = state.users;
    val isLoading = viewModel.state.value.isLoading

    Column() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15F)
        ) {
            Row() {
                Text(text = "$sliderPosition km")

            }

            Row() {
                Slider(
                    value = sliderPosition,
                    onValueChange = { sliderPosition = it },
                    valueRange = maxRange,
                    onValueChangeFinished = { chosenRange = sliderPosition },
                )
            }

            Row(
            ) {
                Button(
                    onClick = { search(context, chosenRange, viewModel, allUsers) },
                    modifier = Modifier
                        .fillMaxWidth(0.25f),
                ) {
                    Text(text = "Search")
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)

        ) {
            if (state.users != null) {
                items(state.users!!) { user ->
                    FinderListItem(user = user, onItemClick = {/**/})
                }
            }
        }
    }
}

fun search(context: Context, chosenRange: Float, viewModel: FinderViewModel, allUsers: List<User>?) {
    val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    var usersInRange: List<User>? = null
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        // return
    }
    fusedLocationProviderClient.getCurrentLocation(
        LocationRequest.QUALITY_HIGH_ACCURACY,
        object : CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                CancellationTokenSource().token

            override fun isCancellationRequested() = false
        }).addOnSuccessListener { location: Location? ->
        if (location == null)
            Toast.makeText(context, "cannot get location", Toast.LENGTH_SHORT)
                .show()
        else {
             //just for testing distance - next 2 lines
            location.latitude = 43.38090
            location.longitude = 16.56144
            //val data = getMockData();
            usersInRange = findUsersInRange(allUsers, location, context, chosenRange)
            viewModel.setUsers(usersInRange)
        }
    }
}

fun findUsersInRange(
    data: List<User>?,
    location: Location,
    context: Context,
    chosenRange: Float
): List<User>? {
    var distance: Float = 0F
    var usersInRange: MutableList<User>? = mutableListOf()
    if (data != null) {
        for (user: User in data) {
            distance = location.distanceTo(getLastKnownLocation(user.lat, user.lng))
            if (toKm(distance) <= chosenRange) {
                usersInRange!!.add(user)
            }
        }
    }
    return usersInRange;
}

fun toKm(chosenRange: Float): Float {
    return chosenRange / 1000
}
