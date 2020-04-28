package com.ilham.made.lastsubmission.viewmodel.searchteam

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ilham.made.lastsubmission.model.TeamModel
import com.ilham.made.lastsubmission.ui.search.team.SearchTeamViewModel
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
class SearchTeamViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var applicationMock: Application
    private lateinit var spySearchTeamViewModel: SearchTeamViewModel

    @Mock
    lateinit var observer: Observer<List<TeamModel>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        applicationMock = Mockito.mock(Application::class.java)
        val searchTeamViewModel =
            SearchTeamViewModel(
                applicationMock
            )
        spySearchTeamViewModel = Mockito.spy(searchTeamViewModel)
    }

    @Test
    fun searchViewModelObserverTest() {
        spySearchTeamViewModel.getListSearchTeam()?.observeForever(observer)
        spySearchTeamViewModel.searchTeam(ArgumentMatchers.anyString())

        Mockito.verify(observer).onChanged(ArgumentMatchers.anyList())
        Assert.assertEquals(null, spySearchTeamViewModel.getListSearchTeam()?.value)
    }

    @Test
    fun searchViewModelMethodTest() {
        spySearchTeamViewModel.getListSearchTeam()
        spySearchTeamViewModel.searchTeam(ArgumentMatchers.anyString())
        spySearchTeamViewModel.searchTeam(ArgumentMatchers.anyString())

        Mockito.verify(spySearchTeamViewModel, Mockito.times(1)).getListSearchTeam()
        Mockito.verify(spySearchTeamViewModel, Mockito.times(2))
            .searchTeam(ArgumentMatchers.anyString())
    }

}