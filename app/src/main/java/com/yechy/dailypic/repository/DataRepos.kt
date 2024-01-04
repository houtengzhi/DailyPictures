package com.yechy.dailypic.repository

import android.util.Log
import com.yechy.dailypic.repository.db.DbRepos
import com.yechy.dailypic.repository.http.ApodService
import com.yechy.dailypic.repository.http.BingService
import com.yechy.dailypic.repository.http.HttpRepos
import com.yechy.dailypic.repository.http.onError
import com.yechy.dailypic.repository.http.onException
import com.yechy.dailypic.repository.http.onSuccess
import com.yechy.dailypic.repository.http.suspendOnError
import com.yechy.dailypic.repository.http.suspendOnException
import com.yechy.dailypic.repository.http.suspendOnSuccess
import com.yechy.dailypic.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*


/**
 *
 * Created by cloud on 2019-08-25.
 */
class DataRepos(private val dbRepos: DbRepos, private val httpRepos: HttpRepos) {

    companion object {
        const val TAG = "DataRepos"

        val SOURCES_TYPE = arrayListOf(SourceType.Bing, SourceType.Apod)
    }

    fun getDailyPictureList(sourceType: Int): Flow<List<PictureInfo>> = flow<List<PictureInfo>> {
        val pictureList = dbRepos.queryPictureList(sourceType)
        if (pictureList.isEmpty()) {
            httpRepos.fetchDailyPictureList(sourceType)
                .suspendOnSuccess {
                    Log.d(TAG, "fetch picture list onSuccess, size=${data.size}")
                    dbRepos.insertPictureList(data)
                    emit(data)
                }
                .onError {
                    Log.e(TAG, "fetch picture list onError, size=${this.toString()}")
                }
                .onException {
                    Log.e(TAG, "fetch picture list onException, size=${this.toString()}")
                }

        } else {
            emit(pictureList)
        }
    }
        .flowOn(Dispatchers.IO)

    fun getPictureSourceList(): Flow<List<SourceInfo>> {
        val flows = mutableListOf<Flow<SourceInfo?>>()
        SOURCES_TYPE.forEach { sourceType ->
            val flow = flow {
                var source = dbRepos.queryPictureSourceWithType(sourceType.value)
                Log.d(TAG, "read ${sourceType.text} lcoal: ${source}")
                if (source == null) {
                    httpRepos.fetchTodayPictureInfo(sourceType.value)
                        .suspendOnSuccess {
                            Log.d(TAG, "fetch ${sourceType.text} today picture info onSuccess")
                            source = SourceInfo(data.url, sourceType.text, sourceType.value)
                            emit(source)
                            dbRepos.insertPictureSource(source!!)
                        }
                        .suspendOnError {
                            Log.e(TAG, "fetch ${sourceType.text} today picture info onError, ${this.toString()}")
                            emit(null)
                        }
                        .suspendOnException {
                            Log.e(TAG, "fetch ${sourceType.text} today picture info onException, ${this.toString()}")
                            emit(null)
                        }

                } else {
                    emit(source)
                }
            }
                .onEach {
                    Log.d(TAG, "emit ${sourceType.text} source: ${it}")
                }
                .flowOn(Dispatchers.IO)
            flows.add(flow)
        }

        return combine(*flows.toTypedArray()) {
            Log.d(TAG, "combine ${it.size}")
            it.toList().filterNotNull()
        }
    }

}
