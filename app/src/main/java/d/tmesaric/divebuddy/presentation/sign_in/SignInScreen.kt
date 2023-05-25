package d.tmesaric.divebuddy.presentation.sign_in

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    val navController = rememberNavController()
    SignInScreen(navController = navController)
}

@Composable
fun SignInScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign in screen", textAlign = TextAlign.Center,
        )

        Button(onClick = { navController.navigate("profile") }) {
            Text("profile screen")
        }
    }
}