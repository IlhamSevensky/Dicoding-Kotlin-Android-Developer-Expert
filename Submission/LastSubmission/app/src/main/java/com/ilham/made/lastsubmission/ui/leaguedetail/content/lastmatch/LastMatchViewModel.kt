package com.ilham.made.lastsubmission.ui.leaguedetail.content.lastmatch

import android.app.Application
import androidx.lifecycle.*
import com.ilham.made.lastsubmission.data.repository.MatchRepository
import com.ilham.made.lastsubmission.model.MatchModel

class LastMatchViewModel(application: Application) : AndroidViewModel(application) {

    private var _listLastMatch = MutableLiveData<List<MatchModel>>()
    private var _leagueId: Int = 0

    val lastMatchState = MatchRepository.getLastMatchState()

    fun setLeagueId(leagueId: Int) {
        if (_leagueId == leagueId) {
            return
        } else {
            _leagueId = leagueId
            _listLastMatch.postValue(emptyList())
            _listLastMatch = MatchRepository.getLastMatchByLeagueFromApi(leagueId)
        }
    }

    fun getLastMatch(): LiveData<List<MatchModel>>? = _listLastMatch

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LastMatchViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LastMatchViewModel(app) as T
            }
            throw IllegalArgumentException("Cannot create ViewModel")
        }

    }

}