package com.yechy.dailypic.ui

import android.graphics.Picture
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
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
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.yechy.dailypic.repository.PictureInfo
import com.yechy.dailypic.ui.gallery.GalleryViewModel
import kotlin.math.roundToInt

/**
 *
 * Created by cloud on 2021/6/4.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryPage(galleryViewModel: GalleryViewModel, navigateUp: () -> Unit) {

    var isToobarVisibile by remember { mutableStateOf(true) }
    val expanded = remember { mutableStateOf(false) }

    ConstraintLayout {
        val(gallery, topbar, bottombar) = createRefs()

        PictureGallery(galleryViewModel) {
            isToobarVisibile = !isToobarVisibile
        }

        if (isToobarVisibile) {
            TopAppBar(
                title = {
                    Text(text = "title")
                },
                modifier = Modifier.constrainAs(topbar) {
                                                        top.linkTo(parent.top)
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Menu, contentDescription = "")
                    }
                },
                actions = {
                    Box() {
                        IconButton(onClick = { expanded.value = true }) {
                            Icon(Icons.Filled.MoreVert, contentDescription = null)
                        }
                        DropdownMenu(expanded = expanded.value,
                            onDismissRequest = { expanded.value = false }) {
//                            DropdownMenuItem(onClick = { expanded.value = false }) {
//                                Text(text = "Settings")
//                            }
                        }
                    }

                }
            )

            Text(text = "description",
                color = Color.Gray,
                modifier = Modifier.constrainAs(bottombar) {
                bottom.linkTo(parent.bottom, 20.dp)
                    start.linkTo(parent.start, 16.dp)
            })

        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PictureGallery(galleryViewModel: GalleryViewModel, onImageTap: ((Offset) -> Unit)?) {
    val dataState: DataState<List<PictureInfo>>? by galleryViewModel.pictureList.observeAsState()
    var pictureList = dataState!!.data
    val pagerState = rememberPagerState()

    if (pictureList == null) {
        pictureList = emptyList()
    }

    HorizontalPager(count = pictureList.size,
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = onImageTap)

            }) { page ->
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