package d.tmesaric.divebuddy.presentation.finder

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import d.tmesaric.divebuddy.R
import d.tmesaric.divebuddy.domain.location.LocationHelper
import d.tmesaric.divebuddy.domain.model.User
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


    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bubbles), // Replace with your image resource
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,

            )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
        ) {
        Row(
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
                    checked = true,
                    onCheckedChange = { isChecked ->  isChecked },
                )
                Text(text = "Search all", color = White)
                Checkbox(
                    checked = false,
                    onCheckedChange = { isChecked ->  isChecked },

                    )
                Text(text = "Global search", color = White)
            }

        }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(CenterHorizontally)
            ) {
                Checkbox(
                    checked = true,
                    onCheckedChange = { isChecked -> isChecked },
                )
                Text(text = "Depth", color = White)
                Checkbox(
                    checked = true,
                    onCheckedChange = { isChecked -> isChecked },
                )
                Text(text = "Dynamic", color = White)
                Checkbox(
                    checked = true,
                    onCheckedChange = { isChecked -> isChecked },
                )
                Text(text = "Static", color = White)
            }
            Row {
                Text(
                    text = "Search range: ${sliderPosition.roundToInt()} km",
                    modifier = Modifier.padding(6.dp),
                    color = White
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

/*            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))
            }*/
            val profilePic1 = painterResource(id = R.drawable.gopr0057)
            val profilePic2 = painterResource(id = R.drawable.gopr0058)

            val user1 = User(1, "Hrvoje Horvat", "horvat@gmail.com", "Croatia",
                depth = 35,
                dynamic = 100,
                staticMinutes = 5,
                staticSeconds = 14)
            val user2 = User(1, "Marko Markić", "markic@gmail.com", "Croatia",
                depth = 20,
                dynamic = 80,
                staticMinutes = 4,
                staticSeconds = 5)
            FinderListItem(
                user = user1,
                onItemClick = { navController.navigate("chat") },
                profilePic = profilePic1,
                depth = user1.depth,
                dynamic = user1.dynamic,
                staticMinutes = user1.staticMinutes,
                staticSeconds = user1.staticSeconds
            )
            FinderListItem(
                user = user2,
                onItemClick = { navController.navigate("chat") },
                profilePic = profilePic2,
                depth = user2.depth,
                dynamic = user2.dynamic,
                staticMinutes = user2.staticMinutes,
                staticSeconds = user2.staticSeconds
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
/*            if (viewModel.filteredUsers.value != null) {
                items(viewModel.filteredUsers.value!!) { user ->
                    if (sliderPosition != 0F) {
                        FinderListItem(user = user, onItemClick = {
                            navController.navigate("chat")
                        })
                    }
                }
            }*/

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
        //locationHelper.askForLocationPermissions()
    }
}


