package net.omisoft.aborovskoy.umoriliviper.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.text.Html
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.toolbar.*
import net.omisoft.aborovskoy.umoriliviper.BuildConfig
import net.omisoft.aborovskoy.umoriliviper.R
import net.omisoft.aborovskoy.umoriliviper.app.App
import net.omisoft.aborovskoy.umoriliviper.ui.detail.di.DaggerDetailComponent
import net.omisoft.aborovskoy.umoriliviper.ui.detail.di.DetailComponent
import net.omisoft.aborovskoy.umoriliviper.ui.detail.di.DetailModule
import net.omisoft.aborovskoy.umoriliviper.app.model.Joke
import javax.inject.Inject

class DetailActivity : AppCompatActivity(), DetailContract.View {

    companion object {
        private const val JOKE = "${BuildConfig.APPLICATION_ID}_JOKE"

        fun launch(context: Context, data: Joke) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(JOKE, data)
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
        if (intent.hasExtra(JOKE)) {
            intent.getParcelableExtra<Joke>(JOKE)
            presenter.onViewCreated(intent.getParcelableExtra(JOKE)!!)
        } else {
            presenter.onEmptyData(R.string.empty_data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }

    override fun publishData(joke: Joke) {
        name.text = joke.desc
        site.text = joke.site
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            desc.text = Html.fromHtml(joke.elementPureHtml, Html.FROM_HTML_MODE_LEGACY)
        } else {
            desc.text = (Html.fromHtml(joke.elementPureHtml))
        }
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
