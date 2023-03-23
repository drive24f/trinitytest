package com.trinity.test.rest.entity

import android.os.Parcelable
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class ContactModel(
    @Id var idBox: Long = 0,
    var idContact: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var dob: String? = null
) : Parcelable