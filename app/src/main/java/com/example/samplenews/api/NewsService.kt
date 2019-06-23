package com.example.samplenews.api

import com.example.samplenews.data.NewsFeedsModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * REST API access points
 */
interface NewsService {

    // Replace "yourkey" with original key
    @GET("svc/mostpopular/v2/viewed/{days}.json?api-key=yourkey")
    fun getMostPopularNews(@Path("days") days: Int): Observable<NewsFeedsModel>

}