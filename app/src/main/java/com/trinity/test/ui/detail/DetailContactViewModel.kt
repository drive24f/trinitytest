package com.trinity.test.ui.detail

import com.trinity.test.db.ContactManager
import com.trinity.test.rest.entity.ContactModel
import com.trinity.test.utils.DisposeApp
import com.trinity.test.utils.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailContactViewModel @Inject constructor(
    private val scd: SchedulerProvider,
    private val contactManager: ContactManager
) : DisposeApp() {

    fun saveContact(model: ContactModel) {
        contactManager.saveContact(model)
    }
}