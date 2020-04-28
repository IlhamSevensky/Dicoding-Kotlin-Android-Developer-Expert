package com.ilham.made.lastsubmission.viewmodel.nextmatch

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ilham.made.lastsubmission.model.MatchModel
import com.ilham.made.lastsubmission.ui.leaguedetail.content.nextmatch.NextMatchViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks

@RunWith(JUnit4::class)
class NextMatchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var applicationMock: Application
    private lateinit var spyNextMatchViewModel: NextMatchViewModel

    @Mock
    lateinit var observer: Observer<List<MatchModel>>

    @Before
    fun setUp() {
        initMocks(this)
        applicationMock = mock(Application::class.java)
        val nextMatchViewModel = NextMatchViewModel(applicationMock)
        spyNextMatchViewModel = spy(nextMatchViewModel)
    }

    @Test
    fun nextMatchViewModelObserverTest() {
        spyNextMatchViewModel.getNextMatch()?.observeForever(observer)
        spyNextMatchViewModel.setLeagueId(1)

        verify(observer).onChanged(anyList())
        Assert.assertEquals(null, spyNextMatchViewModel.getNextMatch()?.value)
    }

    @Test
    fun nextMatchViewModelMethodTest() {
        spyNextMatchViewModel.getNextMatch()
        spyNextMatchViewModel.setLeagueId(1)

        verify(spyNextMatchViewModel, times(1)).getNextMatch()
        verify(spyNextMatchViewModel, times(1)).setLeagueId(1)
    }

}