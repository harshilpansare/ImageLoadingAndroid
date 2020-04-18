package com.sample.imageloading.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sample.imageloading.R
import com.sample.imageloading.databinding.ActivityMainBinding
import com.sample.imageloading.di.PhotoViewModelProvider
import com.sample.imageloading.domain.utils.RxUtils
import com.sample.imageloading.presentation.adapter.ImageRecyclerAdapter
import com.sample.imageloading.presentation.viewmodel.PhotoViewModel
import com.sample.imageloading.presentation.viewmodel.PhotoViewModel.Companion.DEFAULT_SEARCH_TERM
import com.sample.pagination.entity.NetworkState

class MainActivityWithCustomGlide : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewModel: PhotoViewModel
    private lateinit var adapter: ImageRecyclerAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, null, false)
        setContentView(binding.root)

        viewModel = PhotoViewModelProvider.getPhotoViewModel()
        viewModel.init(DEFAULT_SEARCH_TERM)

        binding.actionBar.text = "Custom Image Loading"
        binding.glideSwitch.text = "With Glide"
        binding.glideSwitch.setOnClickListener {
            startActivity(Intent(this, MainActivityWithGlide::class.java))
            finish()
        }

        binding.refreshLayout.setOnRefreshListener(this)
        observeDataSourceUpdates()
        setUpRecyclerView()
        setUpSearch()
        onRefresh()
    }

    @SuppressLint("CheckResult")
    private fun setUpSearch() {
        RxUtils.textChangeObservable(binding.searchEditText)
            .compose(com.sample.pagination.utils.RxUtils.applySchedulers())
            .subscribe { term ->
                if (!term.isBlank()) {
                    viewModel.init(term)
                    onRefresh()
                }
            }
    }

    override fun onRefresh() {
        viewModel.invalidateAllPages()
        observeDataSourceUpdates()
    }

    private fun setUpRecyclerView() {
        adapter = ImageRecyclerAdapter(this, false)
        binding.imageRecycler.adapter = adapter
    }

    private fun observeDataSourceUpdates() {
        viewModel.listLiveData.observe(this, Observer { adapter.submitList(it) })
        viewModel.getNetworkState().observe(this, Observer { networkState ->
            adapter.networkState = networkState ?: return@Observer
            when (networkState) {
                NetworkState.LOADING -> binding.refreshLayout.isRefreshing = true
                NetworkState.EMPTY -> {
                    binding.imageRecycler.visibility = View.GONE
                    binding.refreshLayout.isRefreshing = false
                }
                NetworkState.ERROR -> {
                    Toast.makeText(this, "Something went wrong", LENGTH_SHORT).show()
                    binding.refreshLayout.isRefreshing = false
                }
                NetworkState.SUCCESS -> binding.refreshLayout.isRefreshing = false
            }
        })
    }

    override fun onDestroy() {
        adapter.clearScopedCache()
        super.onDestroy()
    }
}
