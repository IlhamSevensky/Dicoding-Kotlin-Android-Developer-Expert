package com.ilham.made.lastsubmission.viewmodel.team

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ilham.made.lastsubmission.model.TeamModel
import com.ilham.made.lastsubmission.ui.leaguedetail.content.team.TeamViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class TeamViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var applicationMock: Application
    private lateinit var spyTeamViewModel: TeamViewModel

    @Mock
    lateinit var observer: Observer<List<TeamModel>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        applicationMock = Mockito.mock(Application::class.java)
        val teamViewModel = TeamViewModel(applicationMock)
        spyTeamViewModel = Mockito.spy(teamViewModel)
    }

    @Test
    fun teamViewModelObserverTest() {
        spyTeamViewModel.getListTeam()?.observeForever(observer)
        spyTeamViewModel.setLeagueId(1)

        Mockito.verify(observer).onChanged(ArgumentMatchers.anyList())
        Assert.assertEquals(null, spyTeamViewModel.getListTeam()?.value)
    }

    @Test
    fun teamViewModelMethodTest() {
        spyTeamViewModel.getListTeam()
        spyTeamViewModel.setLeagueId(1)

        Mockito.verify(spyTeamViewModel, Mockito.times(1)).getListTeam()
        Mockito.verify(spyTeamViewModel, Mockito.times(1)).setLeagueId(1)
    }

}