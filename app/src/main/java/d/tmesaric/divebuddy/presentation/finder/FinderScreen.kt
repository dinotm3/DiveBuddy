package d.tmesaric.divebuddy.presentation.finder

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import d.tmesaric.divebuddy.domain.location.LocationHelper
import d.tmesaric.divebuddy.ui.theme.DeepBlue
import d.tmesaric.divebuddy.ui.theme.White
import kotlin.math.roundToInt

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
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

    val sliderColors = SliderDefaults.colors(
        thumbColor = Color.Black,
        activeTrackColor = Color.Black,
        inactiveTrackColor = White
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .background(DeepBlue),
    ) {
/*        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.09F)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(CenterVertically)
            ) {
                Checkbox(
                    checked = scubaChecked,
                    onCheckedChange = { isChecked -> scubaChecked = isChecked },
                )
                Text(text = "Pool")
                Checkbox(
                    checked = freedivingChecked,
                    onCheckedChange = { isChecked -> freedivingChecked = isChecked },

                    )
                Text(text = "Sea")
            }
        }*/

        Row {
            Text(
                text = "Search range: ${sliderPosition.roundToInt()} km",
                modifier = Modifier.padding(6.dp)
            )
        }

        Slider(
            colors = sliderColors,
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

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            if (viewModel.filteredUsers.value != null) {
                items(viewModel.filteredUsers.value!!) { user ->
                    if (sliderPosition != 0F) {
                        FinderListItem(user = user, onItemClick = {
                            navController.navigate("chat")
                        })
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
    fusedLocationProviderClient: FusedLocationProviderClient
) {
    if (locationHelper.checkLocationPermission(context)) {
        locationHelper.updateRangeFilter(
            fusedLocationProviderClient,
            context,
            viewModel,
            sliderPosition
        )
    } else {
        locationHelper.askForLocationPermissions()
    }
}


