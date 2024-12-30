package com.zj.composedouban.ui.screens.rank

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.request.ImageRequest
import com.android.internal.R.attr.contentDescription
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.WindowInsets
import com.google.accompanist.insets.statusBarsHeight
import com.zj.composedouban.R
import com.zj.composedouban.data.RankDetail
import com.zj.composedouban.data.rankDetailList
import com.zj.composedouban.util.customBorder
import com.zj.composedouban.viewmodel.RankViewModel

/**
 * 单位都是dp
 */
const val RankTopItemHeight = 300
const val ToolbarHeight = 44

@Composable
fun RankScreen(viewModel: RankViewModel = RankViewModel()) {
    val lazyMovieItems = viewModel.rankItems.collectAsLazyPagingItems()
    val scrollState = rememberLazyListState()
    Box {
        LazyColumn(state = scrollState) {
            item {
                RankTopItem()
            }
            items(lazyMovieItems) {
                it?.let {
                    RankListItem(it)
                }
            }

            lazyMovieItems.apply {
                when (loadState.append) {
                    is LoadState.Loading -> {
                        item { LoadingItem() }
                    }

                    else -> {
                        if (loadState.append.endOfPaginationReached) {
                            item {
                                Text(
                                    text = "没有更多数据了",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 16.dp)
                                        .wrapContentSize().customBorder()
                                )
                            }
                        }
                    }
                }
            }
            // item {
            //     Spacer(modifier = Modifier.height(16.dp))
            // }
        }
        RankHeader(scrollState)
    }
}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        color = Color(0xFF7F6351),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            // Optimize: 这是以内容水平居中，而非上面的fillMaxWidth，666
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
fun RankHeader(scrollState: LazyListState) {
    val insets = LocalWindowInsets.current
    val target = LocalDensity.current.run {
        (RankTopItemHeight-ToolbarHeight).dp.toPx()-insets.statusBars.top
    }
    val scrollPercent: Float = if (scrollState.firstVisibleItemIndex > 0) {
        1f
    } else {
        (scrollState.firstVisibleItemScrollOffset / target).coerceIn(0f, 1f)
    }
    println("statusBarsHeight:${insets.statusBars.top} px , scrollPercent: $scrollPercent")
    val activity = LocalContext.current as Activity
    val backgroundColor = Color(0xFF7F6351)
    Column() {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsHeight()
                .alpha(scrollPercent)
                .background(backgroundColor)
                .customBorder()
        )
        Box(modifier = Modifier.height(ToolbarHeight.dp)) {
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(scrollPercent)
                    .background(backgroundColor)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.mipmap.icon_back),
                    contentDescription = "back",
                    modifier = Modifier
                        .size(52.dp, ToolbarHeight.dp)
                        .padding(16.dp, 12.dp, 8.dp, 12.dp)
                        .clickable {
                            activity.finish()
                        }
                )
                Text(
                    text = "豆瓣电影 Top250",
                    color = Color.White,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.alpha(scrollPercent)
                )
            }

        }
    }
}

@Composable
fun RankTopItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(RankTopItemHeight.dp)
    ) {
        Image(
            painter = rememberCoilPainter(request = "https://img2.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg"),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF7F6351), Color(0x807F6351)),
                        start = Offset(0f, Float.POSITIVE_INFINITY),
                        end = Offset(Float.POSITIVE_INFINITY, 0f)
                    )
                )
        ) {
            Spacer(modifier = Modifier.statusBarsHeight())
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                text = "豆瓣电影 Top250",
                color = Color.White,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(16.dp, 8.dp)
            )
            Text(
                text = "250部 • 82.4万人关注",
                color = Color.White,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(16.dp, 0.dp)
            )
            Text(
                text = "豆瓣用户每天都在对“看过”的电影进行“很差”到“力荐”的评价，豆瓣根据每部影片看过的人数以及该影片所得的评价等综合数据，通过算法分析产生豆瓣电影 Top 250。",
                color = Color.White,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(16.dp, 30.dp, 16.dp, 0.dp)
            )
            Text(
                text = "看过 123/250 部",
                color = Color.White,
                style = MaterialTheme.typography.overline,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun RankListItem(item: RankDetail) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row() {
            Image(
                painter = rememberCoilPainter(
                    request = ImageRequest.Builder(LocalContext.current)
                        .data(item.imgOne)
                        .placeholder(R.drawable.fail) // 加载中占位图
                        .error(R.drawable.fail) // 加载失败占位图
                        .build()
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
            )
            Image(
                painter = rememberCoilPainter(
                    request = ImageRequest.Builder(LocalContext.current)
                        .data(item.imgTwo)
                        .placeholder(R.drawable.faillong) // 加载中占位图
                        .error(R.drawable.faillong) // 加载失败占位图
                        .build()
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
                    .height(120.dp)
                    .weight(2f)
                    .clip(RoundedCornerShape(10.dp))
            )
        }
        Text(
            text = item.title,
            color = Color.Black,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp)
        )
        Row(
            modifier = Modifier.padding(0.dp, 4.dp, 16.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 1..5) {
                Image(
                    painter = painterResource(id = R.mipmap.icon_star),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 2.dp, 0.dp)
                        .size(12.dp)
                )
            }
            Text(
                text = item.score,
                color = Color(0xFFFF8D26),
                style = MaterialTheme.typography.caption
            )
        }
        Text(
            text = item.tip,
            color = Color(0xFF7A7A7A),
            style = MaterialTheme.typography.caption,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(0.dp, 4.dp, 30.dp, 0.dp)
        )
        Text(
            text = item.des,
            color = Color.Black,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(0.dp, 4.dp, 0.dp, 0.dp)
        )
    }
}