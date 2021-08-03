package com.yechy.dailypic.ui

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.Glide
import com.google.accompanist.glide.GlidePainterDefaults
import com.google.accompanist.glide.rememberGlidePainter
import com.yechy.dailypic.R
import com.yechy.dailypic.base.DailyPicApp
import com.yechy.dailypic.repository.SourceInfo
import com.yechy.dailypic.ui.home.MainViewModel
import com.yechy.dailypic.ui.theme.DPTypography
import com.yechy.dailypic.util.GlideApp
import com.yechy.dailypic.util.SOURCE_TYPE_BING

@Composable
fun MainPage(mainViewModel: MainViewModel, navigateToGallery: (Int) -> Unit) {

    val scaffoldState = rememberScaffoldState()
    
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            val expanded = remember { mutableStateOf(false) }
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name))},
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Menu, contentDescription = "")
                    }
                },
                actions = {
                    Box() {
                        IconButton(onClick = { expanded.value = true}) {
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
            )
        }
    ) {
        PictureSourceList(mainViewModel = mainViewModel, navigateToGallery)
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
    Card(modifier = Modifier.clickable(onClick = {navigateToGallery(sourceInfo.id)})) {
        Image(
            painter = rememberGlidePainter(
                request = sourceInfo.url,
                previewPlaceholder = R.drawable.placeholder),
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