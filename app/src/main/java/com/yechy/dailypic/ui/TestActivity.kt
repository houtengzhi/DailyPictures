package com.yechy.dailypic.ui

import android.content.pm.PackageManager
import android.os.Bundle
import com.yechy.dailypic.R
import com.yechy.dailypic.base.BaseActivity

import android.Manifest.permission.ACCESS_FINE_LOCATION

import android.Manifest.permission.CALL_PHONE

import android.Manifest.permission.CAMERA

import android.Manifest.permission.GET_ACCOUNTS

import android.Manifest.permission.READ_CALENDAR

import android.Manifest.permission.READ_CONTACTS

import android.Manifest.permission.READ_EXTERNAL_STORAGE

import android.Manifest.permission.READ_PHONE_STATE

import android.Manifest.permission.RECORD_AUDIO

import android.Manifest.permission.WRITE_CALENDAR

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    override fun onStart() {
        super.onStart()

        val pems = arrayOf(ACCESS_FINE_LOCATION, CALL_PHONE, CAMERA, GET_ACCOUNTS,
            READ_CALENDAR, READ_CONTACTS, READ_EXTERNAL_STORAGE, READ_PHONE_STATE, RECORD_AUDIO,
            WRITE_CALENDAR, WRITE_EXTERNAL_STORAGE)

        val groups = arrayOf(LOCATION_GROUP, PHONE_GROUP, CALENDAR_GROUP, CONTACTS_GROUP, CAMERA_GROUP,
        STORAGE_GROUP, MICROPHONE_GROUP)

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
                val gInfo = pm.getPermissionGroupInfo(info.group, 0)
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
}