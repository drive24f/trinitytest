package com.trinity.test.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<vb : ViewBinding> : Fragment() {

    protected lateinit var bind: vb
    abstract fun getBind(i: LayoutInflater, v: ViewGroup?): vb

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View? {
        bind = getBind(i, c)
        return bind.root
    }

    fun <T : Any, L : LiveData<T>> observe(liveData: L, body: (T?) -> Unit) {
        liveData.observe(viewLifecycleOwner, Observer(body))
    }
}