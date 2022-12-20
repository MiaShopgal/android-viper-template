package net.omisoft.aborovskoy.umoriliviper.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.toolbar.*
import net.omisoft.aborovskoy.umoriliviper.BuildConfig
import net.omisoft.aborovskoy.umoriliviper.R
import net.omisoft.aborovskoy.umoriliviper.app.App
import net.omisoft.aborovskoy.umoriliviper.app.model.TrendingRepo
import net.omisoft.aborovskoy.umoriliviper.ui.detail.di.DaggerDetailComponent
import net.omisoft.aborovskoy.umoriliviper.ui.detail.di.DetailComponent
import net.omisoft.aborovskoy.umoriliviper.ui.detail.di.DetailModule
import javax.inject.Inject

class DetailActivity : AppCompatActivity(), DetailContract.View {

    companion object {
        private const val TRENDING_REPO = "${BuildConfig.APPLICATION_ID}_TRENDING_REPO"

        fun launch(context: Context, data: TrendingRepo) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(TRENDING_REPO, data)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var presenter: DetailPresenter

    val component: DetailComponent by lazy {
        DaggerDetailComponent.builder()
            .appComponent((application as App).component)
            .activity(this)
            .plus(DetailModule())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initView()
        component.inject(this)
        presenter.bindView(this)
        if (intent.hasExtra(TRENDING_REPO)) {
            intent.getParcelableExtra<TrendingRepo>(TRENDING_REPO)
            presenter.onViewCreated(intent.getParcelableExtra(TRENDING_REPO)!!)
        } else {
            presenter.onEmptyData(R.string.empty_data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }

    override fun publishData(trendingRepo: TrendingRepo) {
        name.text = trendingRepo.name
        site.text = trendingRepo.cloneUrl
        desc.text = trendingRepo.description
    }

    override fun showMessage(msg: Int) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun initView() {
        toolbar.setTitle(R.string.detail_title)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white))
        toolbar.setNavigationOnClickListener { presenter.onBackClicked() }
    }

}
