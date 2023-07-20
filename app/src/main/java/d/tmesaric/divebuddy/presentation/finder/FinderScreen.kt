package d.tmesaric.divebuddy.presentation.finder

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import d.tmesaric.divebuddy.domain.model.Country

import d.tmesaric.divebuddy.domain.model.User

@Composable
fun FinderScreen(
    navController: NavController,
) {
    val maxRange = 0f..1000f;
    var sliderPosition by remember { mutableStateOf(0f) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row() {
            Text(text = "$sliderPosition km")

        }
        Row() {
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                valueRange = maxRange
            )
        }

        Row(
        ) {
            Button(
                onClick = { findUsersInRange(context) },
                modifier = Modifier
                    .fillMaxWidth(0.25f),


                ) {
                Text(text = "Search")
            }
        }
    }
}

fun findUsersInRange(context: Context) {
    val fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
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
        return
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
            val lat = location.latitude
            val long = location.longitude

            location.latitude = 43.38090
            location.longitude = 16.56144
            var data = getMockData();
            var userName = ""
            var distance: Float  = 0F
            for (user: User in data) {
                val userLocation: Location = user.lastKnownPosition
                userName = user.name;
                distance = location.distanceTo(userLocation)
            }
            Toast.makeText(context, "Lat: $lat, Long: $long - Distance to $userName is $distance m", Toast.LENGTH_LONG)
                .show()
        }
    }
}

fun getMockData(): List<User> {
    val location1 = Location("location 1")
    var location2: Location?
    var location3: Location?
    var location4: Location?
    var location5: Location?


    location1.latitude = 43.38442
    location1.longitude = 16.55815

    val user1 =
        User("1", "User1", "user1@gmail.com", Country.Croatia, location1)
/*    val user2 = User("2", "User2", "user2@gmail.com", Country.Croatia)
    val user3 = User("3", "User3", "user3@gmail.com", Country.Croatia)
    val user4 = User("4", "User4", "user4@gmail.com", Country.Croatia)
    val user5 = User("5", "User5", "user5@gmail.com", Country.Croatia)*/

    val users = listOf(user1);
    return users;
}
