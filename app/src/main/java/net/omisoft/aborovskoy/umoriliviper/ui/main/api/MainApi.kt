package net.omisoft.aborovskoy.umoriliviper.ui.main.api

import io.reactivex.Single
import net.omisoft.aborovskoy.umoriliviper.app.model.TrendingResult
import retrofit2.http.GET

interface MainApi {

    @GET("/search/repositories?q=Q")
    fun getData(): Single<TrendingResult>
}