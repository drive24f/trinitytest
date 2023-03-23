package com.trinity.test.db

import com.trinity.test.MainApp
import com.trinity.test.extensions.appLog
import com.trinity.test.extensions.tryCatch
import com.trinity.test.rest.entity.ContactModel
import com.trinity.test.rest.entity.ContactModel_

class ContactManager(context: MainApp) {

    private var bubbleBox = context.getBoxStore().boxFor(ContactModel::class.java)

    fun insert(box: MutableList<ContactModel>?) {
        appLog("data: insert ${box}")
        tryCatch {
            box?.map {
                bubbleBox.put(
                    ContactModel(
                        idContact = it.idContact.orEmpty(),
                        firstName = it.firstName.orEmpty(),
                        lastName = it.lastName.orEmpty(),
                        email = it.email.orEmpty(),
                        dob = it.dob.orEmpty()
                    )
                )
            }
        }
    }

    fun getAll(): MutableList<ContactModel> {
        return bubbleBox.all.toMutableList()
    }

    fun clearBox() {
        tryCatch { bubbleBox.removeAll() }
    }

    fun checkData(): Boolean {
        return getAll().size == 0
    }

    fun saveContact(model: ContactModel) {
        val box = bubbleBox.query()
            .equal(ContactModel_.idContact, model.idContact.orEmpty())
            .build()
            .use { it.find() }

        if (box.size >= 1) {
            box.map {
                it.idContact = model.idContact
                it.firstName = model.firstName
                it.lastName = model.lastName
                it.email = model.email
                it.dob = model.dob
                bubbleBox.put(it)
            }
        }
    }
}