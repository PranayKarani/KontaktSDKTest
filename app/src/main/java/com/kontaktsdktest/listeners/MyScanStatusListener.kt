package com.kontaktsdktest.listeners

import android.util.Log
import com.kontakt.sdk.android.ble.exception.ScanError
import com.kontakt.sdk.android.ble.manager.listeners.ScanStatusListener

class MyScanStatusListener : ScanStatusListener {

    override fun onScanStop() {
        Log.i("ed", "scan stopped")
    }

    override fun onScanStart() {
        Log.i("ed", "scan started")
    }

    override fun onScanError(scanError: ScanError?) {
        Log.i("ed", "scan error: ${scanError?.message}")
    }

    override fun onMonitoringCycleStop() {
        // no fucks given, for now
    }

    override fun onMonitoringCycleStart() {
        // no fucks given, for now
    }

}