package net.omisoft.aborovskoy.umoriliviper.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.omisoft.aborovskoy.umoriliviper.R
import net.omisoft.aborovskoy.umoriliviper.app.App
import net.omisoft.aborovskoy.umoriliviper.ui.splash.di.DaggerSplashComponent
import net.omisoft.aborovskoy.umoriliviper.ui.splash.di.SplashComponent
import net.omisoft.aborovskoy.umoriliviper.ui.splash.di.SplashModule
import javax.inject.Inject

class SplashActivity : AppCompatActivity(), SplashContract.View {

    @Inject
    lateinit var presenter: SplashPresenter

    val component: SplashComponent by lazy {
        DaggerSplashComponent.builder()
            .appComponent((application as App).component)
            .activity(this)
            .plus(SplashModule())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        component.inject(this)
        presenter.bindView(this)
        presenter.onViewCreated()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }
}
