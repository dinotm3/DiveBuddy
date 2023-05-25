package d.tmesaric.divebuddy
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import d.tmesaric.divebuddy.ui.theme.DiveBuddyTheme
import d.tmesaric.divebuddy.presentation.profile.ProfileScreen
import d.tmesaric.divebuddy.presentation.sign_in.SignInScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiveBuddyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val userLoggedIn = true
                    val navController = rememberNavController()
                    var startDestination = "sign_in"
                    if (userLoggedIn) {
                        startDestination = "profile"
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