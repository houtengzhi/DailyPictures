package com.yechy.dailypic.repository.http

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import com.yechy.dailypic.repository.PictureInfo
import com.yechy.dailypic.util.DPError
import com.yechy.dailypic.util.SourceType
import io.reactivex.Flowable
import java.util.*
import kotlin.collections.ArrayList

/**
 *
 * Created by cloud on 2019-11-05.
 */
const val FORMAT = "js"

class HttpRepos(val bingService: BingService, val apodService: ApodService) {

    suspend fun fetchDailyPictureList(sourceType: Int): ApiResponse<List<PictureInfo>> {
        if (sourceType == SourceType.Bing.value) {
            return bingService.fetchHPImage(FORMAT, 0, 8, 1, "hp", 1, 3840, 2160)

        } else if (sourceType == SourceType.Apod.value) {
            val calendar = Calendar.getInstance(Locale.US)
            calendar.timeInMillis = System.currentTimeMillis()
            val endDate = SimpleDateFormat(ApodService.DATE_FORMAT).format(calendar.timeInMillis)
            val currentDay = calendar.get(Calendar.DAY_OF_YEAR)
            calendar.set(Calendar.DAY_OF_YEAR, currentDay - 100)
            val startDate = SimpleDateFormat(ApodService.DATE_FORMAT).format(calendar.timeInMillis)
            return apodService.getFeedInDateRange(ApodService.API_KEY, startDate, endDate)
        }
        return ApiResponse.Error(-1, "")
    }

    suspend fun fetchTodayPictureInfo(sourceType: Int): ApiResponse<PictureInfo> {
        if (sourceType == SourceType.Bing.value) {
            return bingService.fetchHPImage(FORMAT, 0, 1, 1, "hp", 1, 3840, 2160)
                .suspendMapSuccess {
                    this.first()
                }
        } else if (sourceType == SourceType.Apod.value) {
            val calendar = Calendar.getInstance(Locale.US)
            calendar.timeInMillis = System.currentTimeMillis()
            val date = SimpleDateFormat(ApodService.DATE_FORMAT).format(calendar.timeInMillis)
            return apodService.getFeedWithDate(ApodService.API_KEY, date)
                .suspendMapSuccess {
                    this.first()
                }
        } else {
            return ApiResponse.Error(DPError.SourceTypeNotFound)
        }
    }
}