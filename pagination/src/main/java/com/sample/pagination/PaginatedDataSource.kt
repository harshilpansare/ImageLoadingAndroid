package com.sample.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.sample.pagination.entity.PaginationProperties.DEFAULT_LIMIT
import com.sample.pagination.entity.PaginationProperties.FIRST_PAGE
import com.sample.pagination.utils.RxUtils.applySchedulers
import com.sample.pagination.entity.NetworkState
import com.sample.pagination.entity.PageData
import com.sample.pagination.entity.PageMetaData
import com.sample.pagination.entity.PaginationEntity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class PaginatedDataSource<T, V : PaginationEntity> : PageKeyedDataSource<Int, V>() {

    val disposables = CompositeDisposable()
    var pageRequest: T? = null
    private var pageSize = DEFAULT_LIMIT

    val metaData = MutableLiveData<PageMetaData>()
    val networkState = MutableLiveData<NetworkState>()

    fun init(pageRequest: T) {
        init(pageRequest, DEFAULT_LIMIT)
    }

    fun init(pageRequest: T, pageSize: Int) {
        this.pageRequest = pageRequest
        this.pageSize = pageSize
    }

    abstract fun loadPage(pageRequest: T, page: Int, callback: LoadCallback<Int, V>)

    protected fun executeObservable(observable: Observable<PageData<V>>, callback: LoadCallback<Int, V>) {
        networkState.postValue(NetworkState.LOADING)
        val d = observable
                .compose(applySchedulers())
                .subscribe({ pageData ->
                    metaData.postValue(pageData.metaData)
                    if (pageData.metaData.page == 1 && pageData.list.isEmpty())
                        networkState.postValue(NetworkState.EMPTY)
                    else
                        networkState.postValue(NetworkState.SUCCESS)
                    callback.onResult(pageData.list, pageData.metaData.page + 1)
                }, { t ->
                    networkState.postValue(NetworkState.ERROR)
                    callback.onError(t)
                })
        disposables.add(d)
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, V>) {
        loadPage(pageRequest
                ?: throw IllegalStateException("Page Request has not been initialized"),
                FIRST_PAGE, object : LoadCallback<Int, V>() {
            override fun onResult(data: MutableList<V>, adjacentPageKey: Int?) {
                callback.onResult(data, null, 2)
            }

            override fun onError(error: Throwable) {
            }

            override fun onRetryableError(error: Throwable) {
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, V>) {
        loadPage(pageRequest
                ?: throw IllegalStateException("Page Request has not been initialized"),
                params.key, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, V>) {
    }

    fun dispose() {
        pageRequest = null
        disposables.dispose()
    }
}
