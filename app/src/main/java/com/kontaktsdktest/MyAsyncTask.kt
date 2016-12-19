package com.kontaktsdktest

import android.app.Activity
import android.app.ProgressDialog
import android.database.Cursor
import android.os.AsyncTask
import android.provider.ContactsContract
import android.widget.Toast

class MyAsyncTask(val ctx: Activity) : AsyncTask<Unit, Int, Cursor>() {

    var pDialog: ProgressDialog? = null

    override fun onPreExecute() {

        pDialog = ProgressDialog(ctx)
        pDialog?.setTitle("Please wait")
        pDialog?.setMessage("counting contacts")
        pDialog?.setCancelable(true)
        pDialog?.show()

    }

    override fun doInBackground(vararg p0: Unit?): Cursor {

        // Just so that we can see the progress dialog :)
        try {
            for (i in 1..5) Thread.sleep(1000)
        } catch(e: Exception) {
            // no fucks given :)
        }

        return ctx.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                arrayOf<String>(ContactsContract.Contacts._ID),
                null,
                null,
                null
        )
    }

    override fun onPostExecute(result: Cursor?) {

        pDialog?.dismiss()
        val text = "found ${result?.count} contacts"
        Toast.makeText(ctx, text, Toast.LENGTH_LONG).show()

    }
}