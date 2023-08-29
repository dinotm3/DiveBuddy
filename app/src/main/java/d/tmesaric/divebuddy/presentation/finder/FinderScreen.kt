package d.tmesaric.divebuddy.presentation.finder

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import d.tmesaric.divebuddy.common.LocationHelper
import kotlin.math.roundToInt

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
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val locationHelper = LocationHelper()

    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15F)
        ) {
            Row {
                Text(text = "${sliderPosition.roundToInt()} km")
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
                            locationHelper,
                            fusedLocationProviderClient
                        )
                    },
                )
            }
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))
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
    }
}

fun handleSliderChange(
    context: Context,
    viewModel: FinderViewModel,
    sliderPosition: Float,
    locationHelper: LocationHelper,
    fusedLocationProviderClient:FusedLocationProviderClient
) {
    if (locationHelper.checkLocationPermission(context)) {
        locationHelper.updateRangeFilter(fusedLocationProviderClient, context, viewModel, sliderPosition)
    } else {
        locationHelper.askForLocationPermissions()
    }
}


