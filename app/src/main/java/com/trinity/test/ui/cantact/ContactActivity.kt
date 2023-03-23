package com.trinity.test.ui.cantact

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.trinity.test.R
import com.trinity.test.core.BaseActivity
import com.trinity.test.databinding.ActivityContactBinding
import com.trinity.test.extensions.appLog
import com.trinity.test.extensions.changeToOrange
import com.trinity.test.rest.entity.ContactModel
import com.trinity.test.ui.cantact.adapter.ContactAdapter
import com.trinity.test.ui.detail.DetailContactActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.io.InputStream

@AndroidEntryPoint
class ContactActivity : BaseActivity<ActivityContactBinding>() {

    private val vm: ContactViewModel by viewModels()

    private val contactAdapter = ContactAdapter()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ContactActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun getBind(i: LayoutInflater) = ActivityContactBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind.btnSearch.changeToOrange(R.drawable.ic_search)
        bind.btnAdd.changeToOrange(R.drawable.ic_search)

        initButton()
        initAdapter()

        vm.insertContact(loadJSONFromAsset()?.toMutableList())
        contactAdapter.setData(vm.getContact())
    }

    override fun onResume() {
        super.onResume()
        contactAdapter.setData(vm.getContact())
    }

    private fun initAdapter() {
        bind.rvContact.apply {
            this.layoutManager = GridLayoutManager(context, (2))
            this.adapter = contactAdapter
            contactAdapter.getAction { m -> DetailContactActivity.start(context, m) }
        }
    }

    private fun initButton() {
        bind.btnSearch.setOnClickListener {

        }

        bind.btnAdd.setOnClickListener {

        }
    }
}