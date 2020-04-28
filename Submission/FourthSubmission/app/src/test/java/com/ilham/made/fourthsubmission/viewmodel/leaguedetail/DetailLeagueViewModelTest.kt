package com.ilham.made.fourthsubmission.viewmodel.leaguedetail

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ilham.made.fourthsubmission.model.LeagueModel
import com.ilham.made.fourthsubmission.ui.leaguedetail.DetailLeagueViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks

@RunWith(JUnit4::class)
class DetailLeagueViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var applicationMock: Application
    lateinit var spyDetailLeagueViewModel: DetailLeagueViewModel

    @Mock
    lateinit var observer: Observer<List<LeagueModel>>

    @Before
    fun setUp() {
        initMocks(this)
        applicationMock = mock(Application::class.java)
        val detailLeagueViewModel = DetailLeagueViewModel(applicationMock)
        spyDetailLeagueViewModel = spy(detailLeagueViewModel)
    }

    @Test
    fun detailLeagueViewModelObserverTest() {
        spyDetailLeagueViewModel.getDetailLeague()?.observeForever(observer)
        spyDetailLeagueViewModel.setLeagueId(14)

        verify(observer).onChanged(anyList())
        Assert.assertEquals(null, spyDetailLeagueViewModel.getDetailLeague()?.value)
    }

    @Test
    fun detailLeagueViewModelMethodTest() {
        spyDetailLeagueViewModel.getDetailLeague()
        spyDetailLeagueViewModel.setLeagueId(anyInt())

        verify(spyDetailLeagueViewModel, times(1)).getDetailLeague()
        verify(spyDetailLeagueViewModel, times(1)).setLeagueId(anyInt())
    }

}