package com.izhal.dicodingsubmission2

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailUserViewModel : ViewModel() {
  companion object {
    private val TAG = DetailUserViewModel::class.java.simpleName
  }

  private var userDetail = MutableLiveData<UserDetail>()

  fun setLogin(login: String) {
    val client = AsyncHttpClient()
    client.setUserAgent("Accept: application/vnd.github.v3+json")

    val url = "https://api.github.com/users/$login"

    client.get(url, object : AsyncHttpResponseHandler() {
      @SuppressLint("SetTextI18n")
      override fun onSuccess(
        statusCode: Int,
        headers: Array<out Header>?,
        responseBody: ByteArray?
      ) {
        try {
          val result = responseBody?.let { String(it) }
          val res = result?.let { JSONObject(result) }

          if (res != null) {
            val user = UserDetail(
              id = res.getInt("id"),
              login = res.getString("login"),
              avatar_url = res.getString("avatar_url"),
              url = res.getString("url"),
              html_url = res.getString("html_url"),
              name = res.getString("name"),
              repos_url = res.getString("repos_url"),
              followers_url = res.getString("followers_url"),
              following_url = res.getString("following_url"),
              location = res.getString("location"),
              bio = res.getString("bio"),
              followers = res.getInt("followers"),
              following = res.getInt("following"),
            )

            userDetail.postValue(user)
          }
        } catch (e: Exception) {
          e.printStackTrace()
          e.message?.let { Log.d(TAG, it) }
        }
      }

      override fun onFailure(
        statusCode: Int,
        headers: Array<out Header>?,
        responseBody: ByteArray?,
        error: Throwable?
      ) {
        val errorMessage = StatusCode.errorMessage(statusCode, error)

        val res = responseBody?.let { String(it) }
        Log.d(TAG, "Status: $errorMessage\nResponse: $res")
      }
    })
  }

  fun getUserDetail(): LiveData<UserDetail> {
    return userDetail
  }
}