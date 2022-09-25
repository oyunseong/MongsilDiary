package com.mongsil.mongsildiary

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlin.coroutines.cancellation.CancellationException

interface EventDelegator<T> {
    suspend fun result(): T
    fun tryEmit(value: T): Boolean
    fun cancel(): Boolean
}

class DelegatedEvent<T : Any> : EventDelegator<T> {
    private val flow = MutableSharedFlow<T?>(extraBufferCapacity = 1)
    override suspend fun result(): T = flow.first() ?: throw CancellationException()

    override fun tryEmit(value: T) = flow.tryEmit(value)

    override fun cancel() = flow.tryEmit(null)
}

sealed class Event

object DialogEvent : Event(), EventDelegator<Boolean> by DelegatedEvent()

object ToastEvent : Event()