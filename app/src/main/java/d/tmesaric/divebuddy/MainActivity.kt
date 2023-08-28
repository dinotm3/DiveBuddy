package d.tmesaric.divebuddy

import android.Manifest
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import d.tmesaric.divebuddy.common.LocationHelper
import d.tmesaric.divebuddy.data.UsersApi
import d.tmesaric.divebuddy.domain.location.LocationClientImpl
import d.tmesaric.divebuddy.domain.model.User
import d.tmesaric.divebuddy.presentation.finder.FinderScreen
import d.tmesaric.divebuddy.ui.theme.DiveBuddyTheme
import d.tmesaric.divebuddy.presentation.profile.ProfileScreen
import d.tmesaric.divebuddy.presentation.sign_in.SignInScreen
import d.tmesaric.divebuddy.utils.redirect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var locationHelper = LocationHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context: Context = this
        if (!locationHelper.checkLocationPermission(context)){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                ),
                0
            )
        }

        setContent {
            DiveBuddyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val userLoggedIn = true
                    val user = User()
                    val navController = rememberNavController()
                    val startDestination = redirect(userLoggedIn)
                    NavHost(navController = navController, startDestination = startDestination) {
                        composable("profile") { ProfileScreen(navController) }
                        composable("sign_in") { SignInScreen(navController) }
                        composable("finder") { FinderScreen(navController) }
                    }
                }
            }
        }
    }
}
