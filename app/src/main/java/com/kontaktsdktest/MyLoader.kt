package com.kontaktsdktest

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import java.util.*

class MyLoader(ctx: Context) : AsyncTaskLoader<Array<String>>(ctx) {
    var arr: Array<String>? = null

    override fun loadInBackground(): Array<String> {

        val ar = ArrayList<String>()

        ar.add("Generated Using Custom Loader")
        ar.add("Swipe again for new numbers")

        try {
            for (i in 1..15) {
                Thread.sleep(200)
                ar.add(Random().nextInt(150).toString())
            }
        } catch(e: Exception) {
            // no fucks given, again :)
        }

        arr = ar.toTypedArray()
        return arr as Array<String>
    }

    override fun deliverResult(data: Array<String>?) {

        if (isStarted) {
            super.deliverResult(data)
        }

    }

    override fun onStartLoading() {
        if (arr != null) {
            deliverResult(arr)
        } else {
            forceLoad()
        }


    }

    override fun onReset() {
        stopLoading()
        arr = null
    }

    override fun onStopLoading() {
        cancelLoad()
    }

}