package d.tmesaric.divebuddy

import android.Manifest
import android.annotation.SuppressLint
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
import d.tmesaric.divebuddy.domain.location.LocationClientImpl
import d.tmesaric.divebuddy.domain.model.User
import d.tmesaric.divebuddy.presentation.finder.FinderScreen
import d.tmesaric.divebuddy.ui.theme.DiveBuddyTheme
import d.tmesaric.divebuddy.presentation.profile.ProfileScreen
import d.tmesaric.divebuddy.presentation.sign_in.SignInScreen

class MainActivity : ComponentActivity() {
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
                    val user = User()
                    val navController = rememberNavController()
                    val fusedLocationProviderClient: FusedLocationProviderClient =
                        LocationServices.getFusedLocationProviderClient(this)
                    val locationClient = LocationClientImpl(this, fusedLocationProviderClient)
                    val startDestination = redirect(user, userLoggedIn, locationClient)

                    NavHost(navController = navController, startDestination = startDestination) {
                        composable("profile") { ProfileScreen(navController) }
                        composable("sign_in") { SignInScreen(navController) }
                        composable("finder") { FinderScreen(navController) }
                    }
                }
            }
        }
    }

    private fun redirect(user: User, userLoggedIn: Boolean, client: LocationClientImpl): String {
        var startDestination = "sign_in"
        if (userLoggedIn) {
            startDestination = "profile"
            val lastKwnLtd = client.getLocation().latitude
            val lastKwnLng = client.getLocation().longitude
/*            Toast.makeText(this, "User long $lastKwnLng, User lat $lastKwnLtd", Toast.LENGTH_LONG)
                .show()*/
            user.lastKnownPosition = client.getLocation()
            Toast.makeText(this, "User last know position: ${user.lastKnownPosition}", Toast.LENGTH_LONG)
                .show()
        }
        return startDestination;
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DiveBuddyTheme {

    }
}