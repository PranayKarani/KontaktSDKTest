package com.kontaktsdktest.listeners

import android.util.Log
import com.kontakt.sdk.android.ble.manager.listeners.SecureProfileListener
import com.kontakt.sdk.android.common.profile.ISecureProfile

class MySecureProfileListener(val tag: String = "ed") : SecureProfileListener {
    override fun onProfileLost(profile: ISecureProfile?) {
        Log.i(tag, "Secure profile Lost: ${profile}")
    }

    override fun onProfilesUpdated(profiles: MutableList<ISecureProfile>?) {

    }

    override fun onProfileDiscovered(profile: ISecureProfile?) {
        Log.i(tag, "Secure profile detected: ${profile}")
    }

}