package d.tmesaric.divebuddy

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationRequest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import d.tmesaric.divebuddy.domain.model.Country
import d.tmesaric.divebuddy.domain.model.User
import d.tmesaric.divebuddy.presentation.finder.FinderScreen
import d.tmesaric.divebuddy.ui.theme.DiveBuddyTheme
import d.tmesaric.divebuddy.presentation.profile.ProfileScreen
import d.tmesaric.divebuddy.presentation.sign_in.SignInScreen

class MainActivity : ComponentActivity() {
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            0
        )

        setContent {
            DiveBuddyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val userLoggedIn = true
                    val navController = rememberNavController()
                    var startDestination = "sign_in"
                    val fusedLocationProviderClient: FusedLocationProviderClient

                    if (userLoggedIn) {
                        startDestination = "profile"
                        fusedLocationProviderClient =
                            LocationServices.getFusedLocationProviderClient(this)
                        fusedLocationProviderClient.getCurrentLocation(
                            LocationRequest.QUALITY_HIGH_ACCURACY,
                            object : CancellationToken() {
                                override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                                    CancellationTokenSource().token

                                override fun isCancellationRequested() = false
                            }).addOnSuccessListener { location: Location? ->
                            if (location == null)
                                Toast.makeText(this, "cannot get location", Toast.LENGTH_SHORT)
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
                                Toast.makeText(this, "Lat: $lat, Long: $long - Distance to $userName is $distance m", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
//                        if (ContextCompat.checkSelfPermission(
//                                this,
//                                Manifest.permission.ACCESS_FINE_LOCATION
//                            ) == PackageManager.PERMISSION_GRANTED
//                        ) {
//                            // TODO
//
//                        }

                    }
                    NavHost(navController = navController, startDestination = startDestination) {
                        composable("profile") { ProfileScreen(navController) }
                        composable("sign_in") { SignInScreen(navController) }
                        composable("finder") { FinderScreen(navController) }
                    }
                }
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
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DiveBuddyTheme {

    }
}