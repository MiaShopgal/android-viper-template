package net.omisoft.aborovskoy.umoriliviper.ui.main

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import net.omisoft.aborovskoy.umoriliviper.app.model.TrendingResult
import net.omisoft.aborovskoy.umoriliviper.ui.main.data.MainRepo

class MainInteractor(private val repo: MainRepo) : MainContract.Interactor {

    private val compositeDisposable = CompositeDisposable()

    override fun getTrendingRepos(onSuccess: (TrendingResult) -> Unit, onError: (Throwable) -> Unit) {
        val disposable = repo.getTrendingRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(onError)
            .doOnSuccess(onSuccess)
            .subscribe()

        compositeDisposable.add(disposable)
    }

    fun dispose() = compositeDisposable.dispose()
}