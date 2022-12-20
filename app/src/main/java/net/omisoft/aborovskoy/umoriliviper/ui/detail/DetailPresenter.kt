package net.omisoft.aborovskoy.umoriliviper.ui.detail

import net.omisoft.aborovskoy.umoriliviper.app.model.TrendingRepo

class DetailPresenter(private val router: DetailContract.Router) : DetailContract.Presenter {

    private var view: DetailContract.View? = null

    override fun bindView(view: DetailContract.View) {
        this.view = view
    }

    override fun unbindView() {
        view = null
    }

    override fun onViewCreated(trendingRepo: TrendingRepo) {
        view?.publishData(trendingRepo)
    }

    override fun onEmptyData(msg: Int) {
        view?.showMessage(msg)
        router.finish()
    }

    override fun onBackClicked() {
        router.finish()
    }
}