package com.rppatil.myapplication.model

import android.util.Log
import com.rppatil.myapplication.data.ApiClient
import com.rppatil.myapplication.data.DataResponse
import com.rppatil.myapplication.data.OperationCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TAG = "CONSOLE"

class Repository : DataSource {

    private var call: Call<DataResponse>? = null

    override fun retrieveData(callback: OperationCallback<JsonData>) {
        call = ApiClient.build()?.getList()
        call?.enqueue(object : Callback<DataResponse> {
            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                response.body()!!.let {
                    if (response.isSuccessful && (it.isSuccess())) {
                        Log.v(TAG, "rows ${it.rows}")
                        callback.onSuccess(it.rows)
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }
}