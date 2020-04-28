package com.ilham.made.thirdsubmission.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.ilham.made.thirdsubmission.data.repository.LeagueRepository
import com.ilham.made.thirdsubmission.model.LeagueModel

class MainViewModel(application: Application): AndroidViewModel(application){

    private var _listLeague = MutableLiveData<List<LeagueModel>>()

    val state = LeagueRepository.getLeagueState()

    init {
        _listLeague = LeagueRepository.getLeagueListFromApi()
    }

    fun getListLeague(): LiveData<List<LeagueModel>>? = _listLeague

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Cannot create ViewModel")
        }

    }

}