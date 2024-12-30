package com.zj.composedouban.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.zj.composedouban.ui.screens.rank.RankScreen
import com.zj.composedouban.ui.theme.ComposeDoubanTheme

class RankDetailActivity : ComponentActivity() {
    companion object {
        /**
         * 路由的管理直接采用Activity传统的方式，新的页面以Activity作为载体，并没有引入Navigation导航那一套，是一种简化的处理
         */
        fun navigate(context: Context) {
            val intent = Intent(context, RankDetailActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            RankDetailPage()
        }
    }
}

@Composable
fun RankDetailPage() {
    ComposeDoubanTheme {
        ProvideWindowInsets {
            RankScreen()
        }
    }
}