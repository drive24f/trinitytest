package com.trinity.test.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import com.trinity.test.core.BaseActivity
import com.trinity.test.databinding.ActivityDetailContactBinding
import com.trinity.test.rest.entity.ContactModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailContactActivity : BaseActivity<ActivityDetailContactBinding>() {

    private val vm: DetailContactViewModel by viewModels()

    companion object {
        fun start(context: Context, model: ContactModel) {
            val intent = Intent(context, DetailContactActivity::class.java)
            intent.putExtra("data_class", model)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun getBind(i: LayoutInflater) = ActivityDetailContactBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initButton()

        bind.inputFirstName.setText(getata()?.firstName)
        bind.inputLastName.setText(getata()?.lastName)
        bind.inputEmail.setText(getata()?.email)
        bind.inputCalendar.setText(getata()?.dob)
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initButton() {
        bind.btnCancle.setOnClickListener {
            finish()
        }

        bind.btnSave.setOnClickListener {
            vm.saveContact(
                ContactModel(
                    idContact = getata()?.idContact.orEmpty(),
                    firstName = bind.inputFirstName.text.toString(),
                    lastName = bind.inputLastName.text.toString(),
                    email = bind.inputEmail.text.toString(),
                    dob = bind.inputCalendar.text.toString(),
                )
            )
            showToast("should have been saved")
            finish()
        }

        bind.btnCalendar.setOnClickListener {

        }
    }

    @Suppress("DEPRECATION")
    private fun getata(): ContactModel? {
        return if (Build.VERSION.SDK_INT >= 33) intent.extras?.getParcelable(
            "data_class", ContactModel::class.java
        ) else intent.extras?.getParcelable("data_class")
    }
}