package com.ilham.made.lastsubmission.ui.leaguedetail.content.team

import android.app.Application
import androidx.lifecycle.*
import com.ilham.made.lastsubmission.data.repository.TeamRepository
import com.ilham.made.lastsubmission.model.TeamModel

class TeamViewModel(application: Application) : AndroidViewModel(application) {

    private var _listTeam = MutableLiveData<List<TeamModel>>()
    private var _leagueId: Int = 0

    val teamState = TeamRepository.getTeamState()

    fun setLeagueId(leagueId: Int) {
        if (_leagueId == leagueId) {
            return
        } else {
            _leagueId = leagueId
            _listTeam.postValue(emptyList())
            _listTeam = TeamRepository.getTeamByLeagueFromApi(leagueId)
        }
    }

    fun getListTeam(): LiveData<List<TeamModel>>? = _listTeam

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TeamViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TeamViewModel(app) as T
            }
            throw IllegalArgumentException("Cannot create ViewModel")
        }

    }

}