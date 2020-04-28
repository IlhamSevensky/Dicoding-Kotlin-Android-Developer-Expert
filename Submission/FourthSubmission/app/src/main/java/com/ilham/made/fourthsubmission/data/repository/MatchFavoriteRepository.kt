package com.ilham.made.fourthsubmission.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ilham.made.fourthsubmission.data.db.dao.MatchFavoriteDatabase
import com.ilham.made.fourthsubmission.data.db.entity.convertToMatchFavoriteModel
import com.ilham.made.fourthsubmission.model.MatchFavoriteModel
import com.ilham.made.fourthsubmission.model.MatchModel
import com.ilham.made.fourthsubmission.utils.Helper

class MatchFavoriteRepository(private val database: MatchFavoriteDatabase) {

    companion object {
        private val TAG = MatchFavoriteRepository::class.java.simpleName
    }

    val favoriteMatchList: LiveData<List<MatchFavoriteModel>>? =
        Transformations.map(database.favoriteMatchDao.getMatchFavoriteList()) {
            it.convertToMatchFavoriteModel()
        }

    fun validateMatch(idEvent: Int): Int {
        return database.favoriteMatchDao.validateMatchFavorite(idEvent)
    }

    fun removeMatchFavorite(idEvent: Int) {
        database.favoriteMatchDao.deleteMatchFavorite(idEvent)
        Log.d(TAG, "Successfully removed match with id : $idEvent from favorite")
    }

    fun addToFavoriteMatch(match: MatchModel) {
        val favoriteMatch = Helper.convertMatchModelToMatchFavoriteModel(match)
        val data = Helper.convertMatchFavoriteModelToMatchFavoriteEntity(favoriteMatch)

        database.favoriteMatchDao.insertMatchFavorite(data)

        Log.d(TAG, "Successfully added match ${data.idEvent} to favorite")
    }

}