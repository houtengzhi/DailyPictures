package com.yechy.dailypic.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
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

@Composable
fun MainPage(mainViewModel: MainViewModel) {

    val scaffoldState = rememberScaffoldState()
    
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name))},
                navigationIcon = {
                    Icon(painter = painterResource(R.drawable.ic_action_dp), contentDescription = "")
                }
            )
        }
    ) {
        PictureSourceList(mainViewModel = mainViewModel)
    }
}

@Composable
fun PictureSourceList(mainViewModel: MainViewModel) {
    val dataState: DataState<List<SourceInfo>>? by mainViewModel.sourceList.observeAsState()
    val pictureSourceList = dataState?.data

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        pictureSourceList?.forEach {
            PictureSourceRow(sourceInfo = it)
        }
    }

}

@Composable
fun PictureSourceRow(sourceInfo: SourceInfo) {
    Card(modifier = Modifier.clickable {  }) {
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
        "Bing")
    PictureSourceRow(sourceInfo)
}

@Preview
@Composable
fun PreviewPicture() {
    Image(
        painter = painterResource(R.drawable.placeholder),
        contentDescription = null)
}