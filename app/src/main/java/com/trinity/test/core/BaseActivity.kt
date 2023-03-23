package com.trinity.test.core

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.google.gson.Gson
import com.trinity.test.rest.entity.ContactModel
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

abstract class BaseActivity<vb : ViewBinding> : AppCompatActivity() {

    protected lateinit var bind: vb
    abstract fun getBind(i: LayoutInflater): vb

    lateinit var context: AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        bind = getBind(layoutInflater)
        setContentView(bind.root)
        context = this
    }

    fun <T : Any, L : LiveData<T>> observe(liveData: L, body: (T?) -> Unit) {
        liveData.observe((this), Observer(body))
    }

    fun loadJSONFromAsset(): Array<ContactModel>? {
        var json: String? = null
        json = try {
            val `is`: InputStream = assets.open("data.json")
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charset.forName(("UTF-8")))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        val toJson = Gson().fromJson(json, Array<ContactModel>::class.java)
        return toJson
    }

    fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}