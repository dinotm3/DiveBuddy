package d.tmesaric.divebuddy.presentation.finder

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import d.tmesaric.divebuddy.domain.model.User
import d.tmesaric.divebuddy.ui.theme.DarkBlue
import d.tmesaric.divebuddy.ui.theme.White


private val IMAGE_MODIFIER = 90.dp

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalCoilApi
@Composable
fun FinderListItem(
    user: User,
    onItemClick: (User) -> Unit,
    profilePic: Painter,
    depth: Int,
    dynamic: Int,
    staticMinutes: Int,
    staticSeconds: Int,
) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = DarkBlue)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick(user) }
        ) {
            Image(
                painter = profilePic,
                contentDescription = null,
                modifier = Modifier
                    .size(IMAGE_MODIFIER)
                    .padding(5.dp)
                    .clip(CircleShape)
            )

            Column(
                modifier = Modifier
                    .weight(1f) // Fill equally in terms of width
                    .padding(8.dp)
                    .align(CenterVertically)
            )
            {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    color = White
                )
                Text(
                    text = user.country,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    color = White

                )
                Text(
                    text = "Level: Freediver",
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    color = White

                )
            }
            Column(
                modifier = Modifier
                    .weight(1f) // Fill equally in terms of width
                    .padding(8.dp)
                    .align(CenterVertically)
            )
            {
                Text(
                    text = "Depth: $depth m",
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    color = White

                )
                Text(
                    text = "Dynamic: $dynamic m",
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    color = White

                )
                Text(
                    text = "Static: $staticMinutes min $staticSeconds",
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    color = White
                )
            }
        }
    }
}

@Preview
@Composable
fun FinderListItemPreview() {
    //val user = User(1, "John Doe")
    //FinderListItem(user = user, onItemClick = {})
}