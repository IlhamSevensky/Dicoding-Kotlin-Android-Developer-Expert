package com.ilham.made.thirdsubmission.ui.leaguedetail.content.nextmatch

import android.app.Application
import androidx.lifecycle.*
import com.ilham.made.thirdsubmission.data.repository.MatchRepository
import com.ilham.made.thirdsubmission.model.MatchModel

class NextMatchViewModel(application: Application) : AndroidViewModel(application) {

    private var _listNextMatch = MutableLiveData<List<MatchModel>>()
    private var _leagueId: Int = 0

    val nextMatchState = MatchRepository.getNextMatchState()

    fun setLeagueId(leagueId: Int) {
        if (_leagueId == leagueId) {
            return
        } else {
            _leagueId = leagueId
            _listNextMatch = MatchRepository.getNextMatchByLeagueFromApi(leagueId)
        }
    }

    fun getNextMatch(): LiveData<List<MatchModel>>? = _listNextMatch

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NextMatchViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NextMatchViewModel(app) as T
            }
            throw IllegalArgumentException("Cannot create ViewModel")
        }

    }

}