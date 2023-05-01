package uz.adkhamjon.mobileprogrammingproject.ui.screens.main

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.adkhamjon.mobileprogrammingproject.data.remote.dto.Hit
import kotlin.math.log


@RootNavGraph(start = true)
@Destination
@Composable
fun MainScreen(
    navigator: DestinationsNavigator,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    var type by remember {
        mutableStateOf("All")
    }
    val images = mainViewModel.getImages(type).collectAsLazyPagingItems()

    Column {
        Toolbar(info = {

        })
        Log.d("TTT", "MainScreen: ${images.itemSnapshotList.items}")
        TabViewPager(
            images.itemSnapshotList.items,
            {
                type = it
            }, item = {

            }
        )
    }
}

@Composable
fun Toolbar(
    info: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFF0C0C0C)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Home",
            color = Color.White,
            fontSize = 16.sp,
            fontStyle = FontStyle.Normal,
            modifier = Modifier.padding(start = 16.dp)
        )

        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier
                .padding(end = 16.dp)
                .size(24.dp)
                .clickable {
                    info.invoke()
                }
        )

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabViewPager(
    imagesList: List<Hit> = emptyList(),
    type: (String) -> Unit,
    item: (String) -> Unit
) {
    val tabs = listOf("All", "Animals", "Buildings", "Food", "Nature", "People", "Technology")
    val pagerState = rememberPagerState(pageCount = tabs.size)
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            divider = {
                Spacer(modifier = Modifier.height(5.dp))
            },
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    height = 2.dp,
                    color = Color.White
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            tabs.forEachIndexed { index, s ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(text = s)
                    },
                    modifier = Modifier
                        .background(Color(0xFF0C0C0C))
                )
            }
        }
        HorizontalPager(state = pagerState) { page ->
            ImagesScreen(imagesList)
            type.invoke(tabs[page])
        }
    }
}

@Composable
fun ImagesScreen(
    list: List<Hit> = emptyList(),
) {
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(list.size) { index ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color(0xFF0C0C0C))
            ) {
                AsyncImage(
                    model = list[index].webformatURL,
                    contentDescription = "Image item",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}