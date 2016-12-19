package com.kontaktsdktest.listeners

import android.util.Log
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleEddystoneListener
import com.kontakt.sdk.android.common.profile.IEddystoneDevice
import com.kontakt.sdk.android.common.profile.IEddystoneNamespace

class MyEddyStoneListener(var tag: String = "ed") : SimpleEddystoneListener() {

    override fun onEddystoneDiscovered(eddystone: IEddystoneDevice?, namespace: IEddystoneNamespace?) {

        Log.i(tag, "detected: ${eddystone}")

    }

    override fun onEddystoneLost(eddystone: IEddystoneDevice?, namespace: IEddystoneNamespace?) {

        Log.i(tag, "lost: ${eddystone?.eid}")

    }
}