package com.trinity.test

import android.os.StrictMode
import androidx.multidex.MultiDexApplication
import com.trinity.test.rest.entity.MyObjectBox
import dagger.hilt.android.HiltAndroidApp
import io.objectbox.BoxStore
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import java.io.IOException
import java.net.SocketException

@HiltAndroidApp
class MainApp : MultiDexApplication() {

    private lateinit var boxStore: BoxStore

    override fun onCreate() {
        super.onCreate()
        strictMode()
        setObjectBox()
        catchUnDeliveryError()
    }

    fun getBoxStore(): BoxStore {
        return boxStore
    }

    private fun setObjectBox() {
        boxStore = MyObjectBox.builder()
            .androidContext(this)
            .build()
    }

    private fun strictMode() {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
    }

    @Suppress("KotlinConstantConditions")
    private fun catchUnDeliveryError() {
        RxJavaPlugins.setErrorHandler { e ->
            if (e is UndeliverableException) {
                // fine, UndeliverableException
                return@setErrorHandler
            }
            if (e is IOException || e is SocketException) {
                // fine, irrelevant network problem or API that throws on cancellation
                return@setErrorHandler
            }
            if (e is InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return@setErrorHandler
            }
            if (e is NullPointerException || e is IllegalArgumentException) {
                // that's likely a bug in the application
                return@setErrorHandler
            }
            if (e is IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                return@setErrorHandler
            }
        }
    }
}