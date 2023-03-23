package com.trinity.test.ui.cantact

import com.trinity.test.db.ContactManager
import com.trinity.test.rest.entity.ContactModel
import com.trinity.test.utils.DisposeApp
import com.trinity.test.utils.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val scd: SchedulerProvider,
    private val contactManager: ContactManager
) : DisposeApp() {

    fun getContact(): MutableList<ContactModel> {
        return contactManager.getAll()
    }

    fun insertContact(models: MutableList<ContactModel>?) {
        if (contactManager.checkData()) {
            contactManager.insert(models)
        }
    }
}