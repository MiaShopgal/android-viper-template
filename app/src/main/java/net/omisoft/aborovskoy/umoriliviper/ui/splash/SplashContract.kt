package net.omisoft.aborovskoy.umoriliviper.ui.splash

interface SplashContract {
    interface View

    interface Presenter {
        fun bindView(view: SplashContract.View)

        fun unbindView()

        fun onViewCreated()
    }

    interface Router {
        fun openMain()
    }
}