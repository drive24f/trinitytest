package com.trinity.test.utils

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class DisposeApp : ViewModel() {

    protected var dispose: Disposable = CompositeDisposable()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun dispose() {
        dispose.dispose()
        compositeDisposable.dispose()
    }
}