package com.rppatil.myapplication.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rppatil.myapplication.R
import com.rppatil.myapplication.di.Injection
import com.rppatil.myapplication.model.JsonData
import com.rppatil.myapplication.viewmodel.DataViewModel
import kotlinx.android.synthetic.main.activity_display.*
import kotlinx.android.synthetic.main.layout_error.*

class DisplayActivity : AppCompatActivity() {

    private lateinit var viewModel: DataViewModel
    private lateinit var adapter: CustomAdapter
    companion object {
        const val TAG = "CONSOLE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        setupViewModel()
        setupUI()
        swipeContainer.setOnRefreshListener {
            viewModel.loadJsonData()
            swipeContainer.isRefreshing = false
        }
    }

    //ui
    private fun setupUI() {
        adapter = CustomAdapter(viewModel.jsonData.value ?: emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory()
        ).get(DataViewModel::class.java)
        viewModel.jsonData.observe(this, renderJsonData)
        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.isEmptyList.observe(this, emptyListObserver)
    }

    //observers
    private val renderJsonData = Observer<List<JsonData>> {
        Log.v(TAG, "data updated $it")
        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
        adapter.update(it)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v(TAG, "isViewLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        progressBar.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v(TAG, "onMessageError $it")
        layoutError.visibility = View.VISIBLE
        layoutEmpty.visibility = View.GONE
        textViewError.text = "Error $it"
    }

    private val emptyListObserver = Observer<Boolean> {
        Log.v(TAG, "emptyListObserver $it")
        layoutEmpty.visibility = View.VISIBLE
        layoutError.visibility = View.GONE
    }

    //If you require updated data, you can call the method "loadJsonData" here
    override fun onResume() {
        super.onResume()
        viewModel.loadJsonData()
    }

}
