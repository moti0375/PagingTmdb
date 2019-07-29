package com.bartovapps.pagingtmdb.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.bartovapps.pagingtmdb.network.apis.TmdbEndpoint
import com.bartovapps.pagingtmdb.network.model.response.Movie
import javax.sql.DataSource

/**
 *   This class in not in use anymore, the DataSource and DataSource.Factory are now provided by
 *   ROOM database.
 *
 * */

class TmdbDsFactory(endpoint: TmdbEndpoint) : androidx.paging.DataSource.Factory<Int, Movie>() {

    private val moviesLiveData = MutableLiveData<PageKeyedDataSource<Int, Movie>>()
    private val dataSource = TmdbDataSource(endpoint)


    override fun create(): androidx.paging.DataSource<Int, Movie> {
        moviesLiveData.postValue(dataSource)
        return dataSource
    }
}