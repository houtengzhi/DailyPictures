package com.yechy.dailypic.ui

import android.graphics.Picture
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import com.google.accompanist.glide.rememberGlidePainter
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

@Composable
fun PictureGallery(galleryViewModel: GalleryViewModel) {
    val dataState: DataState<List<PictureInfo>>? by galleryViewModel.pictureList.observeAsState()
    var pictureList = dataState!!.data
    if (pictureList == null) {
        pictureList = emptyList()
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        items(pictureList) {
            PictureView(pictureInfo = it)
        }

    }

}

@Composable
fun PictureView(pictureInfo: PictureInfo) {
    var scale by remember { mutableStateOf(1f)}
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = rememberGlidePainter(request = pictureInfo.url),
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                )
                .offset{
                    IntOffset(offset.x.roundToInt(), offset.y.roundToInt())
                }
                .pointerInput(Unit) {
                    detectTransformGestures( panZoomLock = true,
                        onGesture = { centroid, pan, zoom, gestureRotate ->
                        rotation += gestureRotate
                        offset += pan
                        scale *= zoom
                    })

                    detectTapGestures(
                        onDoubleTap = {

                        }
                    )
                },
            contentDescription = "",
        contentScale = ContentScale.Fit,
        alignment = Alignment.Center)
    }

}