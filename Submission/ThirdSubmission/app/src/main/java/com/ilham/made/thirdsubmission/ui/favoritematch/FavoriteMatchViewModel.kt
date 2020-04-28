package com.ilham.made.thirdsubmission.ui.favoritematch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ilham.made.thirdsubmission.data.db.dao.getMatchFavoriteDatabase
import com.ilham.made.thirdsubmission.data.repository.MatchFavoriteRepository

class FavoriteMatchViewModel(application: Application): AndroidViewModel(application){

    private val favoriteMatchFavoriteRepository =
        MatchFavoriteRepository(getMatchFavoriteDatabase(application))

    val favoriteMatch = favoriteMatchFavoriteRepository.favoriteMatchList

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavoriteMatchViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FavoriteMatchViewModel(app) as T
            }
            throw IllegalArgumentException("Cannot create ViewModel")
        }

    }

}