package com.x5bart.translatenordic.net

import com.x5bart.translatenordic.net.retrofit.ServerError

class ResponseError {
    var errorCode: ServerError? = null
        private set
    var errorMessage: String? = null
        private set

    constructor(errorCode: Int, errorMessage: String) {
        this.errorCode = ServerError.fromCode(errorCode)
        this.errorMessage = errorMessage
    }

    constructor() {
        ResponseError(ServerError.UNKNOWN_ERROR, "")
    }

    constructor(error: ServerError) {
        ResponseError(error, "")
    }

    constructor(error: ServerError, errorMessage: String) {
        this.errorCode = error
        this.errorMessage = errorMessage
    }
}
