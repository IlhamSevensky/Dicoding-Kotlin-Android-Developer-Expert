package com.ilham.made.lastsubmission.viewmodel.leaguestanding

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ilham.made.lastsubmission.model.StandingsModel
import com.ilham.made.lastsubmission.ui.leaguedetail.content.leaguestanding.LeagueStandingsViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class LeagueStandingsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var applicationMock: Application
    private lateinit var spyLeagueStandingsViewModel: LeagueStandingsViewModel

    @Mock
    lateinit var observer: Observer<List<StandingsModel>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        applicationMock = Mockito.mock(Application::class.java)
        val leagueStandingsViewModel = LeagueStandingsViewModel(applicationMock)
        spyLeagueStandingsViewModel = Mockito.spy(leagueStandingsViewModel)
    }

    @Test
    fun leagueStandingsViewModelObserverTest() {
        spyLeagueStandingsViewModel.getTeamStandings()?.observeForever(observer)
        spyLeagueStandingsViewModel.setLeagueId(1)

        Mockito.verify(observer).onChanged(emptyList())
        Assert.assertEquals(null, spyLeagueStandingsViewModel.getTeamStandings()?.value)
    }

    @Test
    fun leagueStandingsViewModelMethodTest() {
        spyLeagueStandingsViewModel.getTeamStandings()
        spyLeagueStandingsViewModel.setLeagueId(1)

        Mockito.verify(spyLeagueStandingsViewModel, Mockito.times(1)).getTeamStandings()
        Mockito.verify(spyLeagueStandingsViewModel, Mockito.times(1)).setLeagueId(1)
    }

}