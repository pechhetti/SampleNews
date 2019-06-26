package com.example.samplenews.ui.news

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.samplenews.api.NewsService
import com.example.samplenews.data.NewsFeedsModel
import com.example.samplenews.data.Result
import com.example.samplenews.testrules.RxImmediateSchedulerRule
import io.reactivex.Flowable
import io.reactivex.Observable
import org.junit.Assert
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import java.lang.RuntimeException

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
        newsViewModel.newsService = Mockito.mock(NewsService::class.java)
        Mockito.`when`(newsViewModel.newsService.getMostPopularNews(7)).thenReturn(Observable.just(newsFeedsModel))
        newsViewModel.fetchNewsList(7)
        newsViewModel.newsFeeds.observeForever {
            Assert.assertTrue(it.status.equals("OK"))
        }
    }

    @Test
    fun testNewsNavEvents() {
        val newsViewModel = NewsViewModel()
        val newsFeedsModel = Mockito.mock(NewsFeedsModel::class.java)
        Mockito.`when`(newsFeedsModel.status).thenReturn("OK")
        newsViewModel.newsService = Mockito.mock(NewsService::class.java)
        Mockito.`when`(newsViewModel.newsService.getMostPopularNews(7)).thenReturn(Observable.just(newsFeedsModel))
        val test = newsViewModel.newsUiNavigationFlowable.test()
        newsViewModel.fetchNewsList(7)
        test.awaitCount(2)
            .assertValueAt(0, NewsNavEvents.ShowProgressBar)
            .assertValueAt(1, NewsNavEvents.HideProgressBar)
    }

    @Test
    fun testOnListItemClicked() {
        val newsViewModel = NewsViewModel()
        val test = newsViewModel.newsUiNavigationFlowable.test()
        newsViewModel.onListItemClicked(Mockito.mock(Result::class.java))
        test.awaitCount(1)
            .assertValue {
                it == NewsNavEvents.NavigateToNewsDetails
            }
    }
}