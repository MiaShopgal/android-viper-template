package net.omisoft.aborovskoy.umoriliviper.ui.main

import net.omisoft.aborovskoy.umoriliviper.app.model.TrendingRepo
import net.omisoft.aborovskoy.umoriliviper.ui.detail.DetailActivity

class MainRouter(private val activity: MainActivity) : MainContract.Router {
    override fun finish() {
        activity.finish()
    }

    override fun openFullRepo(trendingRepo: TrendingRepo) {
        DetailActivity.launch(activity, trendingRepo)
    }
}