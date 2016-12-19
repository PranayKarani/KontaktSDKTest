package com.kontaktsdktest

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.kontakt.sdk.android.ble.manager.ProximityManager
import com.kontakt.sdk.android.common.KontaktSDK
import com.kontaktsdktest.listeners.MyEddyStoneListener
import com.kontaktsdktest.listeners.MyScanStatusListener
import com.kontaktsdktest.listeners.MySecureProfileListener

/********************************* Whats included ***********************************
 *
 * - Kontakt SDK implementation (completely untested)
 * - Material design: Custom ListView item Layout with CardView, Snackbar, Swipe to Refresh, ActionBar Menu
 * - Asynctask which sses Content provider to count Contacts
 * - Custom Loader for generating random numbers
 * - IntentService that displays Simple Notification on start
 *
 * **********************************************************************************/

class AMain : AppCompatActivity(), LoaderManager.LoaderCallbacks<Array<String>> {


    val tag = "ed"
    val loaderID = 123

    var pm: ProximityManager? = null

    /* Views */
    var actionbar: ActionBar? = null
    var swpRefresher: SwipeRefreshLayout? = null
    var coordlayout: CoordinatorLayout? = null
    var textView: TextView? = null
    var listView: ListView? = null
    var sbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_main)

        // Setting up Kontatk Stuff
        KontaktSDK.initialize("vgVkFbLOyUoJMLCRaWTMySpyiXEmEXBT")
        pm = ProximityManager(this)
        pm?.setEddystoneListener(MyEddyStoneListener())
        pm?.setKontaktSecureProfileListener(MySecureProfileListener())
        pm?.setScanStatusListener(MyScanStatusListener())


        // Setting up views
        actionbar = supportActionBar
        textView = findViewById(R.id.text_view) as TextView
        coordlayout = findViewById(R.id.a_main) as CoordinatorLayout
        listView = findViewById(R.id.list_view) as ListView
        sbar = Snackbar
                .make(coordlayout as CoordinatorLayout,
                        "Generating random numbers...",
                        Snackbar.LENGTH_INDEFINITE)
                .setAction("STOP", {
                    if (pm?.isScanning as Boolean) {
                        stopScan()
                    }
                })
        swpRefresher = findViewById(R.id.swipRefresh) as SwipeRefreshLayout
        swpRefresher?.setOnRefreshListener {

            // hide "Swipe Down" text view if visible
            if (textView?.visibility != View.GONE) {
                textView?.visibility = View.GONE
            }

            startScan()
            sbar?.show()
            supportLoaderManager.initLoader(loaderID, null, this)
        }


    }

    override fun onStart(){
        super.onStart()
    }

    override fun onStop() {
        stopScan()
        super.onStop()
    }

    /**
     * Kontakt Stuff
     *************************************************************************************/
    private fun startScan() {

        pm?.connect {
            pm?.startScanning()
            Log.i(tag, "scanning...")
        }

    }

    private fun stopScan() {

        pm?.stopScanning()
        // hide swipe refresher icon
        if (swpRefresher?.isRefreshing as Boolean) {
            swpRefresher?.isRefreshing = false
        }
        // dismiss snackbar
        if (sbar?.isShown as Boolean) {
            sbar?.dismiss()
        }
        // for generating new set og random nos
        supportLoaderManager.destroyLoader(loaderID)

    }

    /**
     * LoaderCallback stuff
     *************************************************************************************/

    override fun onLoaderReset(loader: Loader<Array<String>>) {
        // to something here
    }

    override fun onLoadFinished(loader: Loader<Array<String>>, data: Array<String>) {

        // show LitView is hidden
        if (listView?.visibility != View.VISIBLE) {
            listView?.visibility = View.VISIBLE
        }
        listView?.adapter = ArrayAdapter<String>(this, R.layout.x_list, R.id.cool_text, data)
        stopScan()

    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Array<String>>? {

        return MyLoader(this)

    }

    /**
     * Action Bar menu stuff
     *************************************************************************************/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.asyncTask -> {

                MyAsyncTask(this).execute()

                return true
            }

            R.id.service -> {

                startService(Intent(this, MyService::class.java))

                return true

            }

            else -> {
                return super.onOptionsItemSelected(item)
            }

        }
    }

}
