package com.example.stopwatch

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StopWatch(
    val startTime: Long,
    val endTime: Long,
    val isRunning: Boolean
) : Parcelable {

     val currentTime: Long
        get() {
            return if(isRunning) System.currentTimeMillis() - startTime
            else endTime - startTime
        }

    companion object {
        val default = StopWatch(0, 0, false)
    }
}

fun StopWatch.start() = StopWatch(System.currentTimeMillis(), 0, true)
fun StopWatch.stop() = StopWatch(startTime, System.currentTimeMillis(), false )
fun StopWatch.resume() = StopWatch(System.currentTimeMillis() - currentTime, 0, true)
