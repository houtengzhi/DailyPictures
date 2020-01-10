package com.yechy.dailypic.repository

import com.yechy.dailypic.repository.db.DbRepos
import com.yechy.dailypic.repository.http.HttpRepos
import com.yechy.dailypic.repository.system.AndroidRepos
import io.reactivex.Flowable


/**
 *
 * Created by cloud on 2019-08-25.
 */
class DataRepos(private val dbRepos: DbRepos, private val androidRepos: AndroidRepos, private val httpRepos: HttpRepos) {

    fun getTodayPicture(): Flowable<PictureInfo> {
        return httpRepos.fetchDailyPictureInfo()
    }
}
