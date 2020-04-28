package com.ilham.made.lastsubmission.ui.leaguedetail.content.leaguestanding

import android.app.Application
import androidx.lifecycle.*
import com.ilham.made.lastsubmission.data.repository.LeagueRepository
import com.ilham.made.lastsubmission.model.StandingsModel

class LeagueStandingsViewModel(application: Application) : AndroidViewModel(application) {

    private var _listTeamStandings = MutableLiveData<List<StandingsModel>>()
    private var _leagueId: Int = 0

    val leagueStandingsState = LeagueRepository.getLeagueStandingsState()

    fun setLeagueId(leagueId: Int) {
        if (_leagueId == leagueId) {
            return
        } else {
            _leagueId = leagueId
            _listTeamStandings.postValue(emptyList())
            _listTeamStandings = LeagueRepository.getLeagueStandingFromApi(leagueId)
        }
    }

    fun getTeamStandings(): LiveData<List<StandingsModel>>? = _listTeamStandings

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LeagueStandingsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LeagueStandingsViewModel(app) as T
            }
            throw IllegalArgumentException("Cannot create ViewModel")
        }

    }

}