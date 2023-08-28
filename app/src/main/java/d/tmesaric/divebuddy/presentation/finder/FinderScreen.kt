package d.tmesaric.divebuddy.presentation.finder

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import d.tmesaric.divebuddy.domain.model.User
import d.tmesaric.divebuddy.domain.model.getLastKnownLocation

@OptIn(ExperimentalCoilApi::class)
@Composable
fun FinderScreen(
    navController: NavController,
) {
    val maxRange = 0f..500f
    var sliderPosition by remember { mutableStateOf(0f) }
    val context = LocalContext.current

    val viewModel: FinderViewModel = hiltViewModel()
    val state = viewModel.state.value
    val isLoading = state.isLoading
    var isMessageBoxVisible: Boolean
    val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15F)
        ) {
            Row {
                Text(text = "$sliderPosition km")
            }

            Row {
                Slider(
                    value = sliderPosition,
                    onValueChange = { sliderPosition = it },
                    valueRange = maxRange,
                    onValueChangeFinished = {
                        handleSliderChange(
                            context,
                            viewModel,
                            sliderPosition,
                            fusedLocationProviderClient
                        )
                    },
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            // Use filteredUsers state instead of state.users
            if (viewModel.filteredUsers.value != null) {
                items(viewModel.filteredUsers.value!!) { user ->
                    if (sliderPosition != 0F){
                        FinderListItem(user = user, onItemClick = {/**/})
                    }
                }
            }
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))
        }
    }
}

@Composable
fun MessageBox(isVisible: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (isVisible) {
            Text(
                text = "Please select distance",
                modifier = Modifier.padding(16.dp),
                color = Color.White
            )
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
        }
    }
}


// Call this function to request permission if needed
fun checkLocationPermission(context: Context): Boolean {
    var hasLocationPermission = false
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        hasLocationPermission = true
    }
    return hasLocationPermission
}

fun askForLocationPermissions() {
    TODO("Not yet implemented")
}

@SuppressLint("MissingPermission")
fun getCurrentUserLocation(
    fusedLocationProviderClient: FusedLocationProviderClient,
                           context: Context,
                           viewModel: FinderViewModel,
                           sliderPosition: Float
){
    fusedLocationProviderClient.getCurrentLocation(
        LocationRequest.QUALITY_HIGH_ACCURACY,
        @SuppressLint("MissingPermission")
        object : CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                CancellationTokenSource().token

            override fun isCancellationRequested() = false
        }
    ).addOnSuccessListener { location: Location? ->
        if (location == null)
            Toast.makeText(
                context,
                "Cannot get location",
                Toast.LENGTH_SHORT
            ).show()
        else {
            // fake location so it works in emulator,
            // comment when testing with real device
            location.latitude = 43.38090
            location.longitude = 16.56144
            viewModel.filterUsersInRange(
                sliderPosition,
                location,
                context
            )
        }
    }
}

fun handleSliderChange(
    context: Context,
    viewModel: FinderViewModel,
    sliderPosition: Float,
    fusedLocationProviderClient: FusedLocationProviderClient
) {
    if (checkLocationPermission(context)) {
        getCurrentUserLocation(fusedLocationProviderClient, context, viewModel, sliderPosition)
    } else {
        askForLocationPermissions()
    }
}
fun findUsersInRange(
    allUsers: List<User>?,
    location: Location,
    context: Context,
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

fun toKm(chosenRange: Float): Float {
    return chosenRange / 1000
}
