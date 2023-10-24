package d.tmesaric.divebuddy

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import d.tmesaric.divebuddy.domain.location.LocationHelper
import d.tmesaric.divebuddy.presentation.chat.ChatViewModel
import d.tmesaric.divebuddy.domain.model.User
import d.tmesaric.divebuddy.presentation.chat.ChatScreen
import d.tmesaric.divebuddy.presentation.finder.FinderScreen
import d.tmesaric.divebuddy.ui.theme.DiveBuddyTheme
import d.tmesaric.divebuddy.presentation.profile.ProfileScreen
import d.tmesaric.divebuddy.presentation.sign_in.SignInScreen
import d.tmesaric.divebuddy.utils.redirect
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import d.tmesaric.divebuddy.presentation.settings.SettingsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var locationHelper = LocationHelper()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context: Context = this
        if (!locationHelper.checkLocationPermission(context)) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                ),
                0
            )
        }

        data class BottomNavigationItem(
            var title: String,
            val selectedIcon: ImageVector,
            val unselectedIcon: ImageVector,
            val hasNews: Boolean,
            val badgeCount: Int? = null
        )
        setContent {
            DiveBuddyTheme {
                val items = listOf(
                    BottomNavigationItem(
                        title = "Finder",
                        selectedIcon = Icons.Filled.Search,
                        unselectedIcon = Icons.Outlined.Search,
                        hasNews = false,
                    ),
                    // get extended icons dependency
                    BottomNavigationItem(
                        title = "Chat",
                        selectedIcon = Icons.Filled.MailOutline,
                        unselectedIcon = Icons.Outlined.MailOutline,
                        hasNews = false,
                        badgeCount = 3 // add variable unread messages
                    ),
                    BottomNavigationItem(
                        title = "Settings",
                        selectedIcon = Icons.Filled.Settings,
                        unselectedIcon = Icons.Outlined.Settings,
                        hasNews = false,
                    ),
                    BottomNavigationItem(
                        title = "Requests",
                        selectedIcon = Icons.Filled.Person,
                        unselectedIcon = Icons.Outlined.Person,
                        hasNews = false,
                        badgeCount = 1
                    ),
                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val userLoggedIn = true
                    val navController = rememberNavController()
                    val startDestination = redirect(userLoggedIn)
                    NavHost(navController = navController, startDestination = startDestination) {
                        composable("profile") { ProfileScreen(navController) }
                        composable("sign_in") { SignInScreen(navController) }
                        composable("finder") { FinderScreen(navController) }
                        composable("chat") { ChatScreen(ChatViewModel()) }
                        composable("settings") { SettingsScreen() }
                    }
                    var selectedItemIndex by rememberSaveable {
                        mutableStateOf(0)
                    }
                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                items.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            selectedItemIndex = index
                                            // navController.navigate(item.title)
                                        },
                                        label = {
                                            Text(text = item.title)
                                        },
                                        alwaysShowLabel = false,
                                        icon = {
                                            BadgedBox(
                                                badge = {
                                                    if(item.badgeCount != null) {
                                                        Badge {
                                                            Text(text = item.badgeCount.toString())
                                                        }
                                                    } else if(item.hasNews) {
                                                        Badge()
                                                    }
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = if (index == selectedItemIndex) {
                                                        item.selectedIcon
                                                    } else item.unselectedIcon,
                                                    contentDescription = item.title
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    ) {
                        when (selectedItemIndex) {
                            0 -> FinderScreen(navController)
                            1 -> ChatScreen(ChatViewModel()) // Example for ChatScreen
                            2 -> SettingsScreen() // Example for SettingsScreen
                        }
                    }
                }
            }
        }
    }
}

