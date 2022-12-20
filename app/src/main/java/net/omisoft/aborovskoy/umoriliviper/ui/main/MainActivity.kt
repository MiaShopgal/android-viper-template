package net.omisoft.aborovskoy.umoriliviper.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import net.omisoft.aborovskoy.umoriliviper.R
import net.omisoft.aborovskoy.umoriliviper.app.App
import net.omisoft.aborovskoy.umoriliviper.app.model.TrendingRepo
import net.omisoft.aborovskoy.umoriliviper.app.model.TrendingResult
import net.omisoft.aborovskoy.umoriliviper.ui.main.adapter.MainAdapter
import net.omisoft.aborovskoy.umoriliviper.ui.main.di.DaggerMainComponent
import net.omisoft.aborovskoy.umoriliviper.ui.main.di.MainComponent
import net.omisoft.aborovskoy.umoriliviper.ui.main.di.MainModule
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var presenter: MainPresenter

    val component: MainComponent by lazy {
        DaggerMainComponent.builder()
            .appComponent((application as App).component)
            .activity(this)
            .plus(MainModule())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        component.inject(this)
        presenter.bindView(this)
        presenter.onViewCreated()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }

    override fun showLoading() {
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        recyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun publishData(data: TrendingResult) {
        val adapter = MainAdapter(data, object : MainAdapter.RepoListener {
            override fun onItemClick(trendingRepo: TrendingRepo) {
                presenter.onItemClicked(trendingRepo)
            }
        })
        recyclerView.adapter = adapter
    }

    override fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun initView() {
        val manager = LinearLayoutManager(this)
            .apply { orientation = LinearLayoutManager.VERTICAL }
        recyclerView.layoutManager = manager
        toolbar.setTitle(R.string.main_title)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white))
        toolbar.setNavigationOnClickListener { presenter.onBackClicked() }
    }
}
