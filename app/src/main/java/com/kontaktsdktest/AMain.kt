package com.kontaktsdktest

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import com.kontakt.sdk.android.ble.device.EddystoneDevice
import com.kontakt.sdk.android.ble.exception.ScanError
import com.kontakt.sdk.android.ble.manager.ProximityManager
import com.kontakt.sdk.android.ble.manager.ProximityManagerContract
import com.kontakt.sdk.android.ble.manager.listeners.ScanStatusListener
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleEddystoneListener
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleSecureProfileListener
import com.kontakt.sdk.android.common.KontaktSDK
import com.kontakt.sdk.android.common.profile.IEddystoneDevice
import com.kontakt.sdk.android.common.profile.IEddystoneNamespace
import com.kontakt.sdk.android.common.profile.ISecureProfile
import java.util.*

class AMain : AppCompatActivity() {

    var pmc: ProximityManagerContract? = null
    var arr : ArrayList<IEddystoneDevice>? = null
    var listView: ListView? = null
    var text: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_main)

        KontaktSDK.initialize("vgVkFbLOyUoJMLCRaWTMySpyiXEmEXBT")

        text = findViewById(R.id.textView) as TextView
        listView = findViewById(R.id.list) as ListView

        var counter = 0;
        pmc = ProximityManager(this)
        pmc?.setEddystoneListener(object : SimpleEddystoneListener(){

            override fun onEddystoneDiscovered(eddystone: IEddystoneDevice?, namespace: IEddystoneNamespace?) {
                counter++;
                Log.i("ed","Discovred: ${eddystone?.distance} -> ${eddystone}")
                //listView?.adapter = null
                if(arr != null){

                    if(!(arr as Array<IEddystoneDevice>).contains(eddystone)){
                        if (eddystone != null) {
                            (arr as ArrayList<IEddystoneDevice>).add(eddystone)
                        }
                    }
                    listView?.adapter = null
                    listView?.adapter = xAdap(this@AMain, arr)
                }
            }

            override fun onEddystonesUpdated(eddystones: MutableList<IEddystoneDevice>?, namespace: IEddystoneNamespace?) {

            }

        })

        pmc?.setKontaktSecureProfileListener(object : SimpleSecureProfileListener() {

            override fun onProfilesUpdated(profiles: MutableList<ISecureProfile>?) {
//                arr = profiles?.sorted()?.toTypedArray() as Array<ISecureProfile>

            }

        })


        pmc?.setScanStatusListener(object: ScanStatusListener{
            override fun onMonitoringCycleStop() {

            }

            override fun onMonitoringCycleStart() {

            }

            override fun onScanError(scanError: ScanError?) {

                text?.text = "Scan error"

            }

            override fun onScanStop() {

                text?.text = "Scan complete"



            }

            override fun onScanStart() {

                text?.text = "Scan started"

            }
        })


    }
    override fun onStart(){
        super.onStart()
        scan()
    }

    private fun scan(){
        pmc?.connect { pmc?.startScanning()
            Log.i("ed", "Scanning started..")

        }

    }

    class xAdap(val mycontext: Activity, sp_arr: ArrayList<IEddystoneDevice>?) : ArrayAdapter<IEddystoneDevice>(mycontext, -1, sp_arr){

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val myView: View
            if(convertView == null){

                myView = mycontext.layoutInflater.inflate(R.layout.x_list,parent, false )

            } else {

                myView = convertView

            }

            val isp = getItem(position)
            val name = isp.name
            val uid = isp.uniqueId
            val battery = isp.batteryPower
            val power = isp.txPower

            val name_v = myView.findViewById(R.id.name) as TextView
            val uid_v = myView.findViewById(R.id.uid) as TextView
            val battery_v = myView.findViewById(R.id.battery) as TextView
            val power_v = myView.findViewById(R.id.power) as TextView

            name_v.text = name
            uid_v.text = "uid: $uid"
            battery_v.text = "$battery%"
            power_v.text = "power: $power"

            return myView
        }

    }

}
