package com.yechy.dailypic.repository.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * Created by cloud on 2019-08-25.
 */
@Entity(tableName = "white_list")
data class AppEntity(
        @ColumnInfo(name = "package_name") val packageName: String,
        @ColumnInfo(name = "app_name") val appName: String,
        @ColumnInfo(name = "icon_res_id") val iconResId: Int) {

        @PrimaryKey(autoGenerate = true)
        var id: Int = 0
}