package com.rppatil.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rppatil.myapplication.data.OperationCallback
import com.rppatil.myapplication.model.JsonData
import com.rppatil.myapplication.model.DataSource

class DataViewModel(private val repository: DataSource) : ViewModel() {

    private val _jsonData = MutableLiveData<List<JsonData>>().apply { value = emptyList() }
    val jsonData: LiveData<List<JsonData>> = _jsonData

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    fun loadJsonData() {
        _isViewLoading.postValue(true)
        repository.retrieveData(object : OperationCallback<JsonData> {
            override fun onError(error: String?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue(error)
            }

            override fun onSuccess(data: List<JsonData>?) {
                _isViewLoading.postValue(false)
                if (data != null) {
                    if (data.isEmpty()) {
                        _isEmptyList.postValue(true)
                    } else {
                        _jsonData.value = data
                    }
                }
            }
        })
    }

}