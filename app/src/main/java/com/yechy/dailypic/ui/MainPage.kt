package com.yechy.dailypic.ui

import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberAsyncImagePainter
import com.yechy.dailypic.R
import com.yechy.dailypic.base.DailyPicApp
import com.yechy.dailypic.repository.SourceInfo
import com.yechy.dailypic.ui.home.MainViewModel
import com.yechy.dailypic.ui.theme.DPTypography
import com.yechy.dailypic.util.SOURCE_TYPE_BING
import me.onebone.toolbar.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(mainViewModel: MainViewModel, navigateToGallery: (Int) -> Unit) {

//    val scaffoldState = rememberCollapsingToolbarScaffoldState()
//
//    CollapsingToolbarScaffold(
//        state = scaffoldState,
//        modifier = Modifier.fillMaxSize(),
//        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
//        toolbar = {
//
//            ProvideTextStyle(value = TextStyle(color = Color.White)) {
//                Toolbar(state = scaffoldState)
//            }
//        }) {
//        PictureSourceList(mainViewModel = mainViewModel, navigateToGallery)
//    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            var moreMenuExpanded by remember { mutableStateOf(false) }
                 LargeTopAppBar(
                     title = {
                         Text(
                             text = stringResource(R.string.app_name),
                             maxLines = 1,
                             overflow = TextOverflow.Ellipsis
                         )
                     },
                     navigationIcon = {
                         IconButton(onClick = {}) {
                             Icon(Icons.Filled.Menu, contentDescription = "")
                         }
                     },
                     actions = {
                         IconButton(onClick = { moreMenuExpanded = true}) {
                             Icon(Icons.Default.MoreVert , contentDescription = "")
                         }
                         MoreMenu(expanded = moreMenuExpanded, onDismissRequest = {
                             moreMenuExpanded = false
                         })
                     },
                     scrollBehavior = scrollBehavior
                 )

        }, content = { innerPadding ->
            PictureSourceList(mainViewModel = mainViewModel, navigateToGallery, innerPadding)
        })

}

@Composable
private fun CollapsingToolbarScope.Toolbar(state: CollapsingToolbarScaffoldState) {
    var al by remember { mutableStateOf(0f)}
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(150.dp)
            .background(MaterialTheme.colorScheme.primary)
            .graphicsLayer {
                val d = (150.dp - 56.dp).roundToPx()
                translationY = -(state.toolbarState.maxHeight - state.toolbarState.height)
                    .coerceAtMost(d)
                    .toFloat()
                al = 1f - (state.toolbarState.maxHeight - state.toolbarState.height)
                    .coerceAtMost(d)
                    .div(d.toFloat())
            }
    ) {
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 30.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(al)
        )
    }
    
    Row(
        modifier = Modifier
            .height(56.dp)
            .road(Alignment.TopStart, Alignment.TopStart),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val expanded = remember { mutableStateOf(false) }
        IconButton(onClick = {}) {
            Icon(Icons.Filled.Menu, contentDescription = "")
        }
        Spacer(modifier = Modifier.width(16.dp))

        Row(
            Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.headlineMedium) {
                Text(
                    text = stringResource(R.string.app_name),
                    modifier = Modifier.alpha(1f - state.toolbarState.progress)
                )
            }
        }

        Row(
            Modifier.fillMaxHeight(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box() {
                IconButton(onClick = { expanded.value = true }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = null)
                }
                DropdownMenu(expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }) {
//                    DropdownMenuItem(onClick = { expanded.value = false }) {
//                        Text(text = "Settings")
//                    }
                }
            }
        }
    }

}

@Composable
private fun MoreMenu(expanded: Boolean, onDismissRequest: () -> Unit) {
    DropdownMenu(expanded = expanded, onDismissRequest = onDismissRequest) {
        DropdownMenuItem(text = {
            Text(text = "Settings")
        }, onClick = { })
        DropdownMenuItem(text = {
            Text(text = "About")
        }, onClick = { })
    }
}

@Composable
fun PictureSourceList(mainViewModel: MainViewModel, navigateToGallery: (Int) -> Unit, contentPaddingValues: PaddingValues) {
    val dataState: DataState<List<SourceInfo>>? by mainViewModel.sourceList.observeAsState()
    var pictureSourceList = dataState?.data

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = contentPaddingValues) {
        if (pictureSourceList == null) {
            pictureSourceList = emptyList()
        }
        items(pictureSourceList!!) { source ->
            PictureSourceRow(sourceInfo = source, navigateToGallery)
        }
    }

}

@Composable
fun PictureSourceRow(sourceInfo: SourceInfo, navigateToGallery: (Int) -> Unit) {
    Card(modifier = Modifier.clickable(onClick = {navigateToGallery(sourceInfo.type)})) {
        Image(painter = rememberAsyncImagePainter(
                model = sourceInfo.url,
                placeholder = painterResource(R.drawable.placeholder)),
            contentDescription = "bing"
        )
        Text(modifier = Modifier.padding(start = 10.dp, top = 10.dp)
            , text = sourceInfo.title,
            style = DPTypography.headlineMedium)
    }
}

@Preview
@Composable
fun PreviewPictureSourceRow() {
    val sourceInfo = SourceInfo("https://cdn.yamap.co.jp/public/image2.yamap.co.jp/production/de575e56da514826896dfebc6a7d408b?h=1080&t=resize&w=1439",
        "Bing", SOURCE_TYPE_BING
    )
    PictureSourceRow(sourceInfo, {})
}

@Preview
@Composable
fun PreviewPicture() {
    Image(
        painter = painterResource(R.drawable.placeholder),
        contentDescription = null)
}