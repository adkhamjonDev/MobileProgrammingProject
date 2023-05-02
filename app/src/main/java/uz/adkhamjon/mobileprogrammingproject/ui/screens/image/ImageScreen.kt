package uz.adkhamjon.mobileprogrammingproject.ui.screens.image

import android.content.Intent
import android.content.Intent.*
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorComposable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.cloudy.Cloudy

const val DEFAULT = "Default"

@Destination
@Composable
fun ImageScreen(
    navigator: DestinationsNavigator,
    image: String = ""
) {
    val optionTypes by remember {
        mutableStateOf(DEFAULT)
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = image,
            contentDescription = "Image item",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        when (optionTypes) {
            DEFAULT -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        BlurButton(
                            icon = Icons.Default.ArrowBack,
                            onClick = {
                                navigator.popBackStack()
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        BlurButton(
                            icon = Icons.Default.KeyboardArrowDown,
                            onClick = {

                            }
                        )
                        BlurButton(
                            icon = Icons.Default.Lock,
                            onClick = {

                            }
                        )
                        BlurButton(
                            icon = Icons.Default.Share
                        ) {
                            //SharIntent(image)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BlurButton(
    icon: @VectorComposable ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.size(48.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = 0.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x99FFFFFF))
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "",
                tint = Color.Black,
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}

@Composable
fun SharIntent(txt: String) {
    val shareIntent = Intent().apply {
        action = ACTION_SEND
        putExtra(EXTRA_TEXT, txt)
        type = "text/plain"
    }

    val chooserIntent = createChooser(shareIntent, "Share Image")
    LocalContext.current.startActivity(chooserIntent)

}