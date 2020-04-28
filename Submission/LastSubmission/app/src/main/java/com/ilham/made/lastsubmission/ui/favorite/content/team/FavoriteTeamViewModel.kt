package com.ilham.made.lastsubmission.ui.favorite.content.team

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ilham.made.lastsubmission.data.db.dao.getTeamFavoriteDatabase
import com.ilham.made.lastsubmission.data.repository.TeamFavoriteRepository

class FavoriteTeamViewModel(application: Application): AndroidViewModel(application){

    private val favoriteTeamFavoriteRepository =
        TeamFavoriteRepository(getTeamFavoriteDatabase(application))

    val favoriteTeam = favoriteTeamFavoriteRepository.favoriteTeamList

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavoriteTeamViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FavoriteTeamViewModel(
                    app
                ) as T
            }
            throw IllegalArgumentException("Cannot create ViewModel")
        }

    }

}