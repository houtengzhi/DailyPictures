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
import androidx.compose.ui.layout.ContentScale
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
import com.yechy.dailypic.util.SourceType
import me.onebone.toolbar.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(mainViewModel: MainViewModel, navigateToGallery: (Int) -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalNavigationDrawer(drawerState = drawerState,
        drawerContent = {
                        ModalDrawerSheet {

                        }
    }, content = {
            val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
            Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    var moreMenuExpanded by remember { mutableStateOf(false) }
                    LargeTopAppBar(
                        colors = TopAppBarDefaults.largeTopAppBarColors(
                        ),
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
    })

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
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = { navigateToGallery(sourceInfo.type) })) {
        Image(painter = rememberAsyncImagePainter(
                model = sourceInfo.url,
                placeholder = painterResource(R.drawable.placeholder),
            contentScale = ContentScale.FillWidth),
            modifier = Modifier.fillMaxWidth(),
            contentDescription = "bing",
            contentScale = ContentScale.FillWidth
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
        "Bing", SourceType.Bing.value
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