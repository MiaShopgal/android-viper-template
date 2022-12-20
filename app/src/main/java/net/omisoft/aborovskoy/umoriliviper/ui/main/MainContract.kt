package net.omisoft.aborovskoy.umoriliviper.ui.main

import io.reactivex.Single
import net.omisoft.aborovskoy.umoriliviper.app.model.TrendingRepo
import net.omisoft.aborovskoy.umoriliviper.app.model.TrendingResult

interface MainContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun publishData(data: TrendingResult)
        fun showMessage(msg: String)
    }

    interface Presenter {

        fun bindView(view: MainContract.View)

        fun unbindView()

        fun onViewCreated()

        fun onItemClicked(trendingRepo: TrendingRepo)

        fun onBackClicked()
    }

    interface Interactor {
        fun getTrendingRepos(onSuccess: (TrendingResult) -> Unit, onError: (Throwable) -> Unit)
    }

    interface Router {
        fun finish()
        fun openFullRepo(trendingResult: TrendingRepo)
    }

    interface Repo {
        fun getTrendingRepos(): Single<TrendingResult>
    }
}