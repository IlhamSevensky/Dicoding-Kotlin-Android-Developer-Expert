package com.ilham.made.fourthsubmission.viewmodel.main

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ilham.made.fourthsubmission.model.LeagueModel
import com.ilham.made.fourthsubmission.ui.main.MainViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks

@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var applicationMock: Application
    lateinit var spyMainViewModel: MainViewModel

    @Mock
    lateinit var observer: Observer<List<LeagueModel>>

    @Before
    fun setUp() {
        initMocks(this)
        applicationMock = mock(Application::class.java)
        val mainViewModel = MainViewModel(applicationMock)
        spyMainViewModel = spy(mainViewModel)
    }

    @Test
    fun mainViewModelObserverTest() {
        spyMainViewModel.getListLeague()?.observeForever(observer)
        spyMainViewModel.refreshListLeague()

        verify(observer).onChanged(emptyList())
        Assert.assertEquals(null, spyMainViewModel.getListLeague()?.value)
    }

    @Test
    fun mainViewModelMethodTest() {
        spyMainViewModel.getListLeague()
        spyMainViewModel.refreshListLeague()

        verify(spyMainViewModel, times(1)).getListLeague()
        verify(spyMainViewModel, times(1)).refreshListLeague()
    }

}