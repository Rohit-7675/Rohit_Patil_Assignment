package com.rppatil.myapplication.data

import com.rppatil.myapplication.model.JsonData

data class DataResponse(val rows: List<JsonData>?) {
    fun isSuccess(): Boolean = (rows!!.isNotEmpty())
}