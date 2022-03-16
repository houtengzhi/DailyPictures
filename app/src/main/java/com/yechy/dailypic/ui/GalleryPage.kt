package com.yechy.dailypic.ui

import android.graphics.Picture
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.yechy.dailypic.repository.PictureInfo
import com.yechy.dailypic.ui.gallery.GalleryViewModel
import kotlin.math.roundToInt

/**
 *
 * Created by cloud on 2021/6/4.
 */
@Composable
fun GalleryPage(galleryViewModel: GalleryViewModel, navigateUp: () -> Unit) {

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState) {
        PictureGallery(galleryViewModel)
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PictureGallery(galleryViewModel: GalleryViewModel) {
    val dataState: DataState<List<PictureInfo>>? by galleryViewModel.pictureList.observeAsState()
    var pictureList = dataState!!.data

    if (pictureList == null) {
        pictureList = emptyList()
    }

    HorizontalPager(count = pictureList.size, modifier = Modifier.fillMaxSize()) { page ->
        PictureView(pictureInfo = pictureList[page])
    }

}

@Composable
fun PictureView(pictureInfo: PictureInfo) {
    var scale by remember { mutableStateOf(1f)}
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val maxScale = 3f
    val minScale = 1f


    Box(modifier = Modifier
        .background(Color.Black)
        .clip(RectangleShape)
        .fillMaxSize()
        .pointerInput(Unit) {
            forEachGesture {
                awaitPointerEventScope {
                    awaitFirstDown()
                    do {
                        val event = awaitPointerEvent()
                        scale *= event.calculateZoom()
                        if (scale > 1f) {
                            val pan = event.calculatePan()
                            Log.d("PictureView", "pan=${pan}")
                            offset = offset.plus(pan)
                            rotation += event.calculateRotation()

                        } else {
                            scale = 1f
                            offset = Offset.Zero
                        }
                        Log.d("PictureView", "offset=${offset}")
                    } while (event.changes.any { it.pressed })
                }
            }
        }) {
        Image(
            painter = rememberAsyncImagePainter(model = pictureInfo.url),
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer(
                    scaleX = maxOf(minScale, minOf(maxScale, scale)),
                    scaleY = maxOf(minScale, minOf(maxScale, scale)),
                    translationX = offset.x,
                    translationY = offset.y
                ),
            contentDescription = "",
            contentScale = ContentScale.Fit)
    }

}