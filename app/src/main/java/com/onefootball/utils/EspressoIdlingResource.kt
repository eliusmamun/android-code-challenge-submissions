package com.onefootball.utils

import androidx.test.espresso.idling.CountingIdlingResource

/**
 * Used to validate asynchronous operations in UI tests
 */
object EspressoIdlingResource {
    private const val RESOURCE = "GLOBAL"

    @JvmField val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment(){
        countingIdlingResource.increment()
    }

    fun decrement(){
        if(!countingIdlingResource.isIdleNow){
            countingIdlingResource.decrement()
        }


    }
}