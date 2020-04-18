package com.rppatil.myapplication.model

import com.rppatil.myapplication.data.OperationCallback

interface DataSource {
    fun retrieveData(callback: OperationCallback<JsonData>)
    fun cancel()
}