package com.izhal.dicodingsubmission2

class StatusCode {
  companion object {
    fun errorMessage(statusCode: Int, error: Throwable?): String {
      var result = ""

      result = when (statusCode) {
        401 -> "$statusCode : Bad Request"
        403 -> "$statusCode : Forbidden"
        404 -> "$statusCode : Not Found"
        else -> "$statusCode : ${error?.message}"
      }

      return result
    }
  }
}