package uz.adkhamjon.mobileprogrammingproject.ui.screens.image

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
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
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.cloudy.Cloudy
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.adkhamjon.mobileprogrammingproject.R
import uz.adkhamjon.mobileprogrammingproject.utils.AndroidDownloader
import java.io.IOException

const val DEFAULT = "default"
const val EDIT = "edit"
const val SHARE = "share"
const val DOWNLOAD = "download"
const val WALLPAPER = "wallpaper"
const val BACK = "back"

@Destination
@Composable
fun ImageScreen(
    navigator: DestinationsNavigator,
    image: String = ""
) {
    val optionTypes by remember {
        mutableStateOf(DEFAULT)
    }
    var actions by remember {
        mutableStateOf("")
    }
    val rememberCoroutineScope = rememberCoroutineScope()
    var drawable: Drawable? = null
    val loading = ImageLoader(LocalContext.current)
    val request = ImageRequest.Builder(LocalContext.current)
        .data(image)
        .build()
    rememberCoroutineScope.launch {
        drawable = (loading.execute(request) as SuccessResult).drawable
    }
    val context = LocalContext.current
    when (actions) {
        EDIT -> {

        }
        BACK -> {
            navigator.popBackStack()
            actions = ""
        }
        SHARE -> {
            SharIntent(txt = image)
            actions = ""
        }
        DOWNLOAD -> {
            val downloader = AndroidDownloader(LocalContext.current)
            downloader.downloadFile(image)
            Toast.makeText(
                LocalContext.current,
                "Image Downloaded Successfully!!",
                Toast.LENGTH_SHORT
            ).show()
            actions = ""
        }
        WALLPAPER -> {

            setWallpaper(context, (drawable as BitmapDrawable).bitmap)
            actions = ""
        }
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
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        BlurButton(
                            icon = Icons.Default.ArrowBack,
                            onClick = {
                                actions = BACK
                            }
                        )
                        BlurButton(
                            icon = Icons.Default.Edit,
                            onClick = {
                                actions = EDIT
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
                                actions = DOWNLOAD
                            }
                        )
                        BlurButton(
                            icon = Icons.Default.Lock,
                            onClick = {
                                actions = WALLPAPER
                            }
                        )
                        BlurButton(
                            icon = Icons.Default.Share
                        ) {
                            actions = SHARE
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

@Composable
fun setWallpaper(context: Context, bitmap: Bitmap) {
    val wallpaperManager = WallpaperManager.getInstance(context)
    wallpaperManager.setBitmap(bitmap)
    Toast.makeText(
        context,
        "Wallpaper Set Successfully!!",
        Toast.LENGTH_SHORT
    ).show()
}