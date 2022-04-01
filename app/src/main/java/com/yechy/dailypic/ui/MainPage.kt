package com.yechy.dailypic.ui

import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import coil.compose.rememberAsyncImagePainter
import com.yechy.dailypic.R
import com.yechy.dailypic.base.DailyPicApp
import com.yechy.dailypic.repository.SourceInfo
import com.yechy.dailypic.ui.home.MainViewModel
import com.yechy.dailypic.ui.theme.DPTypography
import com.yechy.dailypic.util.SOURCE_TYPE_BING
import me.onebone.toolbar.*

@Composable
fun MainPage(mainViewModel: MainViewModel, navigateToGallery: (Int) -> Unit) {

    val scaffoldState = rememberCollapsingToolbarScaffoldState()

    CollapsingToolbarScaffold(
        state = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            
            ProvideTextStyle(value = TextStyle(color = Color.White)) {
                Toolbar(state = scaffoldState)
            }
            
//            val expanded = remember { mutableStateOf(false) }
//            val textSize = (18 + (30 - 18) * scaffoldState.toolbarState.progress).sp
//            val elevation = (if (scaffoldState.toolbarState.progress == 0f) 4 else 0).dp
//            Box(
//                modifier = Modifier
//                    .background(MaterialTheme.colors.primary)
//                    .fillMaxWidth()
//                    .height(150.dp)
//                    .pin()
//            ) {
//                Text(
//                    text = stringResource(R.string.app_name),
//                    fontSize = 30.sp,
//                    color = Color.White,
//                    modifier = Modifier
//                        .align(Alignment.Center)
//                        .alpha(scaffoldState.toolbarState.progress)
//                        .offset(y = -(75 * (1 - scaffoldState.toolbarState.progress)).dp)
//                )
//            }

//            TopAppBar(
//                title = { Text(
//                    text = stringResource(R.string.app_name),
//                    modifier = Modifier.alpha(1f - scaffoldState.toolbarState.progress))},
//                navigationIcon = {
//                    IconButton(onClick = {}) {
//                        Icon(Icons.Filled.Menu, contentDescription = "")
//                    }
//                },
//                actions = {
//                    Box() {
//                        IconButton(onClick = { expanded.value = true}) {
//                            Icon(Icons.Filled.MoreVert, contentDescription = null)
//                        }
//                        DropdownMenu(expanded = expanded.value,
//                            onDismissRequest = { expanded.value = false }) {
//                            DropdownMenuItem(onClick = { expanded.value = false }) {
//                                Text(text = "Settings")
//                            }
//                        }
//                    }
//
//                },
//                elevation = elevation
//            )
        }) {
        PictureSourceList(mainViewModel = mainViewModel, navigateToGallery)
    }

}

@Composable
private fun CollapsingToolbarScope.Toolbar(state: CollapsingToolbarScaffoldState) {
    var al by remember { mutableStateOf(0f)}
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(150.dp)
            .background(MaterialTheme.colors.primary)
            .graphicsLayer {
                val d = (150.dp - 56.dp).roundToPx()
                translationY = -(state.toolbarState.maxHeight - state.toolbarState.height)
                    .coerceAtMost(d)
                    .toFloat()
                al = 1f - (state.toolbarState.maxHeight - state.toolbarState.height).coerceAtMost(d).div(d.toFloat())
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
            ProvideTextStyle(value = MaterialTheme.typography.h6) {
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
                    DropdownMenuItem(onClick = { expanded.value = false }) {
                        Text(text = "Settings")
                    }
                }
            }
        }
    }

}

@Composable
fun PictureSourceList(mainViewModel: MainViewModel, navigateToGallery: (Int) -> Unit) {
    val dataState: DataState<List<SourceInfo>>? by mainViewModel.sourceList.observeAsState()
    var pictureSourceList = dataState?.data

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
            style = DPTypography.h4)
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