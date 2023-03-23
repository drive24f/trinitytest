package com.trinity.test.utils

import io.reactivex.*

class SchedulerProvider(private val bgScheduler: Scheduler, private val fgScheduler: Scheduler) {

    fun <T> getObservable(): (Observable<T>) -> Observable<T> {
        return { observable: Observable<T> ->
            observable.subscribeOn(bgScheduler).observeOn(fgScheduler)
        }
    }

    fun <T> getSingle(): (Single<T>) -> Single<T> {
        return { single: Single<T> ->
            single.subscribeOn(bgScheduler).observeOn(fgScheduler)
        }
    }

    fun getCompletable(): (Completable) -> Completable {
        return { completable: Completable ->
            completable.subscribeOn(bgScheduler).observeOn(fgScheduler)
        }
    }

    fun <T> getForFlowable(): (Flowable<T>) -> Flowable<T> {
        return { flowable: Flowable<T> ->
            flowable.subscribeOn(bgScheduler).observeOn(fgScheduler)
        }
    }
}