package com.trinity.test.ui.splash

import androidx.lifecycle.MutableLiveData
import com.trinity.test.utils.DisposeApp
import com.trinity.test.utils.Resource
import com.trinity.test.utils.SchedulerProvider
import com.trinity.test.utils.success
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val scd: SchedulerProvider
) : DisposeApp() {

    var timerResult = MutableLiveData<Resource<Int>>()

    fun startTimer() {
        dispose = Observable.range(0, 2)
            .flatMap { v -> Observable.just(v).delay((2 - v).toLong(), TimeUnit.SECONDS) }
            .compose(scd.getObservable())
            .doOnTerminate { timerResult.success() }
            .subscribe { }
    }
}