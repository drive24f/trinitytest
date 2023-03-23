package com.trinity.test.rest.entity

data class ErrorResponse(
    var code: Int? = null,
    var other: String? = null,
    var message: String? = null
)