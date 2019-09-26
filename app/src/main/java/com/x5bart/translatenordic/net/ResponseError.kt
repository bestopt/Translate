package com.x5bart.translatenordic.net


class ResponseError {
    var errorCode: ServerError? = null
        private set
    private var errorMessage: String? = null

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
    fun getErrorMessage():String?{
        return errorMessage
    }


    }

