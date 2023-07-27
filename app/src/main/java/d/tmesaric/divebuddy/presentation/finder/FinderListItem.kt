package d.tmesaric.divebuddy.presentation.finder

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import d.tmesaric.divebuddy.domain.model.User


private val IMAGE_MODIFIER = 90.dp
@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalCoilApi
@Composable
fun FinderListItem(
    user: User,
    onItemClick: (User) -> Unit
) {

    Card(
        modifier = Modifier
            .padding(
/*                horizontal = LocalSpacing.current.medium,
                vertical = LocalSpacing.current.small*/
            )
            .fillMaxWidth(),
/*        elevation = LocalSpacing.current.mini,
        backgroundColor = Color.DarkGray,
        shape = RoundedCornerShape(corner = CornerSize(LocalSpacing.current.large))*/
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick(user) }
/*                .padding(LocalSpacing.current.small),
            horizontalArrangement = Arrangement.SpaceBetween*/
        ) {
            //val painter = rememberImagePainter(data = File(article.picturePath!!))
/*            Image(
                // painter = painter
                painter = painterResource(id = d.tmesaric.divebuddy.R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier.size(IMAGE_MODIFIER)
            )*/
            Text(
                text = "${user.name}. \nCountry: ${user.country}\n",
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .align(Alignment.Bottom))
        }
    }
}