package com.phonepe.phonepemovies.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.phonepe.phonepemovies.utils.State.Status.*
import kotlinx.coroutines.Dispatchers

fun <T, A> performGetOperation(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> State<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<State<T>> =
    liveData(Dispatchers.IO) {
        emit(State.loading())
        val source = databaseQuery.invoke().map {
            State.success(it)
        }

        emitSource(source)

        val responseStatus = networkCall.invoke()
        Log.d("responseStatus8085", "performGetOperation: $responseStatus")
        if (responseStatus.status == SUCCESS) {

            responseStatus.data?.let { saveCallResult(it) }

        } else if (responseStatus.status == ERROR) {
            responseStatus.message?.let {
                emit(State.error(responseStatus.message))
                emitSource(source)
            }
        }
    }