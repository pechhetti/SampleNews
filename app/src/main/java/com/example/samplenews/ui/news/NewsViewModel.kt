package com.example.samplenews.ui.news

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.*
import com.example.samplenews.api.NYTimeNewsApi
import com.example.samplenews.data.NewsFeedsModel
import com.example.samplenews.data.Result
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers

class NewsViewModel : ViewModel(), LifecycleObserver {

    private val composeDisposable = CompositeDisposable()
    private val newsUiNavigationProcessor = PublishProcessor.create<NewsNavEvents>().toSerialized()
    val newsUiNavigationFlowable = newsUiNavigationProcessor as Flowable<NewsNavEvents>

    var newsFeeds = MutableLiveData<NewsFeedsModel>()
    var selectedItem = MutableLiveData<Result>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        fetchNewsList(1)
    }

    @SuppressLint("CheckResult")
    fun fetchNewsList(days: Int) {
        composeDisposable.add(
            NYTimeNewsApi.getNewsService().getMostPopularNews(days)
                .doOnSubscribe {
                    newsUiNavigationProcessor.onNext(NewsNavEvents.ShowProgressBar)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    newsUiNavigationProcessor.onNext(NewsNavEvents.Error)
                }
                .subscribe(this::displayResults, this::handleError))
    }

    private fun displayResults(newsFeedsModel: NewsFeedsModel) {
        newsFeedsModel.let {
            newsFeeds.value = it
            newsUiNavigationProcessor.onNext(NewsNavEvents.HideProgressBar)
        }
    }

    private fun handleError(throwable: Throwable) {
        Log.e("handleError()", throwable.message)
        newsUiNavigationProcessor.onNext(NewsNavEvents.Error)
    }

    fun onListItemClicked(result: Result) {
        selectedItem.value = result
        newsUiNavigationProcessor.onNext(NewsNavEvents.NavigateToNewsDetails)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        composeDisposable.dispose()
    }
}

sealed class NewsNavEvents {
    object ShowProgressBar : NewsNavEvents()
    object HideProgressBar : NewsNavEvents()
    object Error : NewsNavEvents()
    object NavigateToNewsDetails : NewsNavEvents()
}