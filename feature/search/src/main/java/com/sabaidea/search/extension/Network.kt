package com.sabaidea.search.extension

import com.sabaidea.search.model.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

fun <T> networkRequest(request: suspend () -> Response<T>): Flow<State<T>> = flow {

    emit(State.onLoading())

    try {
        request().run {
            val body = body()
            if (isSuccessful && body != null)
                emit(State.onSuccess(body))
            else
                emit(State.onError())
        }
    } catch (e: Exception) {
        emit(State.onException(e))
    }
}.flowOn(Dispatchers.IO)