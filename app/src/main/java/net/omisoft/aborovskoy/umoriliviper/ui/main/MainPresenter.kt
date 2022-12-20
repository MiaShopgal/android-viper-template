package net.omisoft.aborovskoy.umoriliviper.ui.main

import net.omisoft.aborovskoy.umoriliviper.app.model.TrendingRepo

class MainPresenter(private val router: MainContract.Router, private val interactor: MainInteractor) :
    MainContract.Presenter {

    private var view: MainContract.View? = null

    override fun bindView(view: MainContract.View) {
        this.view = view
    }

    override fun unbindView() {
        view = null
        interactor.dispose()
    }

    override fun onViewCreated() {
        view?.showLoading()
        interactor.getTrendingRepos({
            view?.hideLoading()
            view?.publishData(it)
        }, this::onError)
    }

    override fun onItemClicked(trendingRepo: TrendingRepo) {
        router.openFullRepo(trendingRepo)
    }

    override fun onBackClicked() {
        router.finish()
    }

    private fun onError(error: Throwable) {
        view?.hideLoading()
        error.message?.let { view?.showMessage(it) }
    }
}