package d.tmesaric.divebuddy.presentation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import d.tmesaric.divebuddy.R
import d.tmesaric.divebuddy.ui.theme.DarkBlue

@Composable
fun SettingsScreen() {
    var text by remember { mutableStateOf("") }
    
    Box(
    modifier = Modifier
    .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bubbles), // Replace with your image resource
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize(),
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Text(text = "Profile settings", color = Color.White)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(1.dp, DarkBlue)
            ) {
                Text(text = "Name: Dino Tretinjak Mesarić", modifier = Modifier.weight(1f).align(CenterVertically).padding(6.dp), color = Color.White)
                Button(
                    onClick = {
/*                    if (text.isNotBlank()) {
                        messages = messages + Message(text, true)
                        text = ""
                    }*/
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(text = "Edit")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(1.dp, DarkBlue)
            ) {
                Text(text = "Maximum depth: 35m", modifier = Modifier.weight(1f).align(CenterVertically).padding(6.dp), color = Color.White)
                Button(
                    onClick = {
/*                    if (text.isNotBlank()) {
                        messages = messages + Message(text, true)
                        text = ""
                    }*/
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(text = "Edit")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(1.dp, DarkBlue)
            ) {
                Text(text = "Maximum dynamic: 100m", modifier = Modifier.weight(1f).align(CenterVertically).padding(6.dp), color = Color.White)
                Button(
                    onClick = {
/*                    if (text.isNotBlank()) {
                        messages = messages + Message(text, true)
                        text = ""
                    }*/
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(text = "Edit")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(1.dp, DarkBlue)
            ) {
                Text(text = "Maximum static: 5 min 10 sec", modifier = Modifier.weight(1f).align(CenterVertically).padding(6.dp), color = Color.White)
                Button(
                    onClick = {
/*                    if (text.isNotBlank()) {
                        messages = messages + Message(text, true)
                        text = ""
                    }*/
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(text = "Edit")
                }
            }
            Text(text = "Other Settings", color = Color.White)

            var soundOnOff by remember { mutableStateOf(true) }
            var allowLocation by remember { mutableStateOf(true) }
            var additionalSetting1 by remember { mutableStateOf(false) }
            var additionalSetting2 by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(1.dp, DarkBlue)
                ) {
                    Text(text = "Sound", modifier = Modifier.weight(1f).align(CenterVertically).padding(6.dp), color = Color.White)
                    // Add a Switch here instead of Button
                    Switch(
                        checked = true, // Set the initial state of the switch
                        onCheckedChange = { /* Handle switch state change here */ },
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(1.dp, DarkBlue)
                ) {
                    Text(text = "Show me in search", modifier = Modifier.weight(1f).align(CenterVertically).padding(6.dp), color = Color.White)
                    // Add a Switch here instead of Button
                    Switch(
                        checked = true, // Set the initial state of the switch
                        onCheckedChange = { /* Handle switch state change here */ },
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

            }
        }
    }
}