package com.yechy.dailypic.repository

import com.yechy.dailypic.repository.db.DbRepos
import com.yechy.dailypic.repository.http.HttpRepos
import io.reactivex.Flowable


/**
 *
 * Created by cloud on 2019-08-25.
 */
class DataRepos(private val dbRepos: DbRepos, private val httpRepos: HttpRepos) {

    fun getTodayPicture(): Flowable<List<PictureInfo>> {
        return httpRepos.fetchDailyPictureInfo()
    }
}
