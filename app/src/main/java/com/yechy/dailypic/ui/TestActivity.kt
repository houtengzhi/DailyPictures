package com.yechy.dailypic.ui

import android.Manifest.permission.*
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.yechy.dailypic.R
import com.yechy.dailypic.base.BaseActivity
import com.yechy.dailypic.databinding.ActivityTestBinding
import com.yechy.dailypic.util.L


/**
 *
 * Created by cloud on 2021/3/16.
 */
class TestActivity: BaseActivity() {

    companion object {
        const val LOCATION_GROUP = "android.permission-group.LOCATION"
        const val PHONE_GROUP = "android.permission-group.PHONE"
        const val CAMERA_GROUP = "android.permission-group.CAMERA"
        const val CONTACTS_GROUP = "android.permission-group.CONTACTS"
        const val CALENDAR_GROUP = "android.permission-group.CALENDAR"
        const val STORAGE_GROUP = "android.permission-group.STORAGE"
        const val MICROPHONE_GROUP = "android.permission-group.MICROPHONE"

    }

    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)

        binding.btnSnackBar.setOnClickListener {

            val intent = Intent(this, TransparentActivity::class.java)
            startActivity(intent)

        }
    }

    override fun onStart() {
        super.onStart()

        val pems = arrayOf(
            ACCESS_FINE_LOCATION, CALL_PHONE, CAMERA, GET_ACCOUNTS,
            READ_CALENDAR, READ_CONTACTS, READ_EXTERNAL_STORAGE, READ_PHONE_STATE, RECORD_AUDIO,
            WRITE_CALENDAR, WRITE_EXTERNAL_STORAGE
        )

        val groups = arrayOf(
            LOCATION_GROUP, PHONE_GROUP, CALENDAR_GROUP, CONTACTS_GROUP, CAMERA_GROUP,
            STORAGE_GROUP, MICROPHONE_GROUP
        )

        var message: String = ""
//        pems.forEach {
////            var reason = getRationale(it)
////            L.d("Permission","${it} : ${reason}")
//
//            var group = getGroup(it)
//            message = "${message}\n${it} :  ${group}"
//        }

        groups.forEach {
            var reason = getRationaleFromGroup(it)
            message = "${message}\n${it} : ${reason}"
        }
        L.d("Permission", message)
    }

    private fun getRationale(permission: String): String? {
        var name: String? = null
            val pm: PackageManager = packageManager
            try {
                val info = pm.getPermissionInfo(permission, 0)
                val gInfo = info.group?.let { pm.getPermissionGroupInfo(it, 0) }
                name = info.loadLabel(pm).toString()
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
        return name
    }

    private fun getRationaleFromGroup(group: String): String? {
        var name: String? = null
        val pm: PackageManager = packageManager
        try {
            val gInfo = pm.getPermissionGroupInfo(group, 0)
            name = gInfo.loadLabel(pm).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return name
    }

    private fun getGroup(permission: String): String? {
        val pm: PackageManager = packageManager
        try {
            val info = pm.getPermissionInfo(permission, 0)
            return info.group
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    private fun showSnackbar(view: View, duration: Int) {
        val s = SpannableString("Snackbar")
        s.setSpan(ForegroundColorSpan(Color.WHITE), 0, s.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        val snackbar = Snackbar.make(view, s, duration)
        val layout: Snackbar.SnackbarLayout = snackbar.getView() as Snackbar.SnackbarLayout
        layout.getChildAt(0).setVisibility(View.GONE)
        layout.setBackground(null)
        val content: View =
            LayoutInflater.from(view.context).inflate(R.layout.layout_snackbar_with_progress, null)
        (content.findViewById<View>(R.id.text) as TextView).setText(s)
        layout.setPadding(0, 0, 0, 0)
        layout.addView(content, 0)
        snackbar.show()
    }
}