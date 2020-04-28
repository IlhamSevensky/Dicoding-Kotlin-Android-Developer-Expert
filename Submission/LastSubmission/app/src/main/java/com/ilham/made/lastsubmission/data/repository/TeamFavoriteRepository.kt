package com.ilham.made.lastsubmission.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ilham.made.lastsubmission.data.db.dao.TeamFavoriteDatabase
import com.ilham.made.lastsubmission.data.db.entity.convertToTeamFavoriteModel
import com.ilham.made.lastsubmission.model.TeamFavoriteModel
import com.ilham.made.lastsubmission.model.TeamModel
import com.ilham.made.lastsubmission.utils.Helper.convertTeamFavoriteModelToTeamFavoriteEntity
import com.ilham.made.lastsubmission.utils.Helper.convertTeamModelToTeamFavoriteModel

class TeamFavoriteRepository(private val database: TeamFavoriteDatabase) {

    companion object {
        private val TAG = TeamFavoriteRepository::class.java.simpleName
    }

    val favoriteTeamList: LiveData<List<TeamFavoriteModel>>? =
        Transformations.map(database.favoriteTeamDao.getTeamFavoriteList()) {
            it.convertToTeamFavoriteModel()
        }

    fun validateTeam(idTeam: Int): Int {
        return database.favoriteTeamDao.validateTeamFavorite(idTeam)
    }

    fun removeTeamFavorite(idTeam: Int) {
        database.favoriteTeamDao.deleteTeamFavorite(idTeam)
        Log.d(TAG, "Successfully removed team with id : $idTeam from favorite")
    }

    fun addToFavoriteTeam(team: TeamModel) {
        val favoriteTeam = convertTeamModelToTeamFavoriteModel(team)
        val data = convertTeamFavoriteModelToTeamFavoriteEntity(favoriteTeam)

        database.favoriteTeamDao.insertTeamFavorite(data)

        Log.d(TAG, "Successfully added team ${data.idTeam} to favorite")
    }

}