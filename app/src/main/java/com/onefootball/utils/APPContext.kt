package com.onefootball.utils

import android.content.Context

class APPContext {

    companion object {
        private lateinit var context: Context

        fun setContext(cont: Context) {
            context=cont
        }

        fun getContext() = context
    }

}