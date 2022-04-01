package com.yechy.dailypic.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * Created by cloud on 2021/3/18.
 */
@Entity(tableName = "source_list")
data class SourceInfo constructor(@ColumnInfo(name = "cover_url") val url: String,
                                  @ColumnInfo(name = "title") val title: String,
                                  @PrimaryKey @ColumnInfo(name = "type") val type: Int) {
}
