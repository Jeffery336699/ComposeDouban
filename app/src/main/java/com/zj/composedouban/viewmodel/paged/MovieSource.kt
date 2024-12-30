package com.zj.composedouban.viewmodel.paged

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zj.composedouban.data.RankDetail
import com.zj.composedouban.data.rankDetailList
import kotlinx.coroutines.delay

class MovieSource : PagingSource<Int, RankDetail>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RankDetail> {
        return try {
            val nextPage = params.key ?: 1
            val movieListResponse =
                rankDetailList.toTypedArray().map { it.copy(title = "${it.title}-${nextPage}页") }
            if (nextPage > 1) {
                delay(2000)
            }
            LoadResult.Page(
                data = movieListResponse,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage == 4) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RankDetail>): Int? {
        return null
    }
}