package com.ilham.made.fourthsubmission.viewmodel.lastmatch

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ilham.made.fourthsubmission.model.MatchModel
import com.ilham.made.fourthsubmission.ui.leaguedetail.content.lastmatch.LastMatchViewModel
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
class LastMatchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var applicationMock: Application
    lateinit var spyLastMatchViewModel: LastMatchViewModel

    @Mock
    lateinit var observer: Observer<List<MatchModel>>

    @Before
    fun setUp() {
        initMocks(this)
        applicationMock = mock(Application::class.java)
        val lastMatchViewModel = LastMatchViewModel(applicationMock)
        spyLastMatchViewModel = spy(lastMatchViewModel)
    }

    @Test
    fun lastMatchViewModelObserverTest() {
        spyLastMatchViewModel.getLastMatch()?.observeForever(observer)
        spyLastMatchViewModel.setLeagueId(1)

        verify(observer).onChanged(anyList())
        Assert.assertEquals(null, spyLastMatchViewModel.getLastMatch()?.value)
    }

    @Test
    fun lastMatchViewModelMethodTest() {
        spyLastMatchViewModel.getLastMatch()
        spyLastMatchViewModel.setLeagueId(1)

        verify(spyLastMatchViewModel, times(1)).getLastMatch()
        verify(spyLastMatchViewModel, times(1)).setLeagueId(1)
    }

}