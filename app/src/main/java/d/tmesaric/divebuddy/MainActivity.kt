package d.tmesaric.divebuddy

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import d.tmesaric.divebuddy.domain.location.LocationService
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
                        fusedLocationProviderClient.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY, object : CancellationToken() {
                            override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

                            override fun isCancellationRequested()= false

                        }).addOnSuccessListener { location: Location? ->
                            if (location == null)
                                Toast.makeText(this, "cannot get location", Toast.LENGTH_SHORT).show()
                            else {
                                val lat = location.latitude
                                val long = location.longitude
                                Toast.makeText(this, "Lat: $lat, Long: $long", Toast.LENGTH_LONG).show()

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
                    }

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DiveBuddyTheme {

    }
}