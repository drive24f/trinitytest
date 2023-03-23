package com.trinity.test.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.trinity.test.core.BaseActivity
import com.trinity.test.databinding.ActivitySplashBinding
import com.trinity.test.ui.cantact.ContactActivity
import com.trinity.test.utils.ResourceStatus
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val vm: SplashViewModel by viewModels()

    override fun getBind(i: LayoutInflater) = ActivitySplashBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeData()
    }

    override fun onResume() {
        super.onResume()
        vm.startTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        vm.dispose()
    }

    private fun observeData() {
        observe(vm.timerResult) { result ->
            if (result?.status == ResourceStatus.SUCCESS) {
                ContactActivity.start(context = this)
                this.finish()
            }
        }
    }
}