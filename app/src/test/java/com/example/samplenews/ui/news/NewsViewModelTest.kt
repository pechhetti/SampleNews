package com.example.samplenews.ui.news

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.samplenews.api.NewsService
import com.example.samplenews.data.NewsFeedsModel
import com.example.samplenews.testrules.RxImmediateSchedulerRule
import io.reactivex.Observable
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class NewsViewModelTest {

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testFetchNewsList() {
        val newsViewModel = NewsViewModel()
        val newsFeedsModel = Mockito.mock(NewsFeedsModel::class.java)
        Mockito.`when`(newsFeedsModel.status).thenReturn("OK")
        val newsService = Mockito.mock(NewsService::class.java)
        Mockito.`when`(newsService.getMostPopularNews(7)).thenReturn(Observable.just(newsFeedsModel))
        newsViewModel.newsFeeds.observeForever {
            assert(it.status === "OK")
        }
    }

    @Test
    fun testNavigateToNewsDetailsEvent() {
        val newsViewModel = NewsViewModel()
        val newsFeedsModel = Mockito.mock(NewsFeedsModel::class.java)
        Mockito.`when`(newsFeedsModel.status).thenReturn("OK")
        val newsService = Mockito.mock(NewsService::class.java)
        Mockito.`when`(newsService.getMostPopularNews(7)).thenReturn(Observable.just(newsFeedsModel))
        with(newsViewModel) {
            newsUiNavigationFlowable.subscribe {
                it == NewsNavEvents.NavigateToNewsDetails
            }
        }
    }

    @Test
    fun testErrorEvent() {
        val newsViewModel = NewsViewModel()
        val newsService = Mockito.mock(NewsService::class.java)
        Mockito.`when`(newsService.getMostPopularNews(7)).thenReturn(Observable.error(Throwable()))
        with(newsViewModel) {
            newsUiNavigationFlowable.subscribe {
                it == NewsNavEvents.Error
            }
        }
    }
}