package com.trinity.test.utils

import com.trinity.test.rest.entity.ErrorResponse

data class Resource<T> constructor(
    var status: ResourceStatus,
    var data: T? = null,
    val error: ErrorResponse? = null
)

data class ResourceData<T> constructor(var data: T? = null)

enum class ResourceStatus { LOADING, SUCCESS, ERROR }