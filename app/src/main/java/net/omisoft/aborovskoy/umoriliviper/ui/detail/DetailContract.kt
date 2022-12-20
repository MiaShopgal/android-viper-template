package net.omisoft.aborovskoy.umoriliviper.ui.detail

import net.omisoft.aborovskoy.umoriliviper.app.model.TrendingRepo

interface DetailContract {
    interface View {
        fun publishData(trendingRepo: TrendingRepo)

        fun showMessage(msg: Int)
    }

    interface Presenter {
        fun bindView(view: DetailContract.View)

        fun unbindView()

        fun onViewCreated(trendingRepo: TrendingRepo)

        fun onBackClicked()

        fun onEmptyData(msg: Int)
    }

    interface Router {
        fun finish()
    }
}