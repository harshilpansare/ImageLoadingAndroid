package com.sample.pagination

import androidx.annotation.CallSuper
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.ComputableLiveData
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.sample.pagination.entity.NetworkState
import com.sample.pagination.entity.PaginationEntity
import com.sample.pagination.entity.PaginationProperties
import java.util.concurrent.Executors

abstract class PaginatedViewModel<Params, E : PaginationEntity>(
    val dataSource: PaginatedDataSource<Params, E>
) : BaseViewModel() {

    lateinit var listLiveData: LiveData<PagedList<E>>
    var params: Params? = null

    @CallSuper
    open fun init(params: Params) {
        this.params = params
        dataSource.init(params)
        initLiveData()
    }

    private fun initLiveData() {
        listLiveData = createLiveData(dataSource)
    }

    private val pagedListConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(PaginationProperties.DEFAULT_LIMIT)
        .setPageSize(PaginationProperties.DEFAULT_LIMIT).build()

    private fun <D> createLiveData(dataSource: DataSource<Int, D>): LiveData<PagedList<D>> {
        val executor = Executors.newFixedThreadPool(5)
        return object : ComputableLiveData<PagedList<D>>(executor) {
            private var mList: PagedList<D>? = null
            override fun compute(): PagedList<D>? {
                do {
                    mList = PagedList.Builder(dataSource, pagedListConfig)
                        .setNotifyExecutor(ArchTaskExecutor.getMainThreadExecutor())
                        .setFetchExecutor(executor)
                        .build()
                } while (mList!!.isDetached)
                return mList
            }
        }.liveData
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return dataSource.networkState
    }

    fun invalidateAllPages() {
        initLiveData()
    }

    override fun onCleared() {
        super.onCleared()
        dataSource.disposables.dispose()
    }
}